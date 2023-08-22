package com.aeon.exchangeratesapp.ui.ratelist

import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.DataResult
import com.aeon.exchangeratesapp.domain.DataResult.Error
import com.aeon.exchangeratesapp.domain.DataResult.Loading
import com.aeon.exchangeratesapp.domain.DataResult.Success
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateListInteractor
import com.aeon.exchangeratesapp.domain.sort.SortInteractor
import com.aeon.exchangeratesapp.ui.base.BaseViewModel
import com.aeon.exchangeratesapp.ui.base.ExchangeRatesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRatesListViewModel @Inject constructor(
    private val exchangeRateListInteractor: ExchangeRateListInteractor,
    favouritesInteractor: FavouritesInteractor,
    private val currencyInteractor: CurrencyInteractor,
    private val sortInteractor: SortInteractor,
) : BaseViewModel(favouritesInteractor, currencyInteractor, sortInteractor) {

    private val _exchangeRateDataFlow =
        MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val exchangeRateDataFlow: StateFlow<ExchangeRatesUiState> = _exchangeRateDataFlow

    private var loadRatesJob: Job? = null

    init {
        observeSortMode()
        observeBaseCurrency()
    }

    fun onRefreshData() {
        loadExchangeRates()
    }

    private fun observeSortMode() {
        viewModelScope.launch {
            sortInteractor.observeSortMode(this)
                .collect {
                    loadExchangeRates()
                }
        }
    }

    private fun observeBaseCurrency() {
        viewModelScope.launch {
            currencyInteractor.observeBaseCurrency(this)
                .collect {
                    loadExchangeRates()
                }
        }
    }

    private fun loadExchangeRates() {
        loadRatesJob?.cancel()
        loadRatesJob = viewModelScope.launch {
            val baseCurrency = currencyInteractor.getBaseCurrency()
            exchangeRateListInteractor.getExchangeRateList(baseCurrency)
                .map { applySort(it) }
                .onEach { updateSavedValues(it) }
                .flowOn(Dispatchers.IO)
                .collect { updateExchangeRateFlow(it) }
        }
    }

    private suspend fun applySort(dataResult: DataResult<ExchangeRateDtoResult>): DataResult<ExchangeRateDtoResult> {
        if (dataResult is Success) {
            val sortMode = sortInteractor.getSortMode()
            dataResult.data.exchangeRateDtoList = applySort(dataResult.data.exchangeRateDtoList, sortMode)
        }
        return dataResult
    }

    private suspend fun updateSavedValues(dataResult: DataResult<ExchangeRateDtoResult>) {
        if (dataResult is Success) {
            dataResult.data.let {
                currencyInteractor.updateCurrencyCodes(dataResult.data.exchangeRateDtoList.map { it.currencyCode }
                    .toSet())
            }
            // update trading pair rate values in case if prices have changed
            favouritesInteractor.updateAllFavouritesValues(dataResult.data.exchangeRateDtoList)
            observeFavourites()
        }
    }

    private fun updateExchangeRateFlow(dataResult: DataResult<ExchangeRateDtoResult>) {
        _exchangeRateDataFlow.update {
            when (dataResult) {
                is Loading -> ExchangeRatesUiState.Loading
                is Success -> ExchangeRatesUiState.Success(dataResult.data)
                is Error -> ExchangeRatesUiState.Error(dataResult.exception)
            }
        }
    }
}

