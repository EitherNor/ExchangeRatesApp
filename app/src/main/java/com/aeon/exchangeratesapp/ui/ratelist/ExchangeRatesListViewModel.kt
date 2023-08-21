package com.aeon.exchangeratesapp.ui.ratelist

import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.DataResult.Error
import com.aeon.exchangeratesapp.domain.DataResult.Loading
import com.aeon.exchangeratesapp.domain.DataResult.Success
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateListInteractor
import com.aeon.exchangeratesapp.ui.base.BaseViewModel
import com.aeon.exchangeratesapp.ui.base.ExchangeRatesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRatesListViewModel @Inject constructor(
    private val exchangeRateListInteractor: ExchangeRateListInteractor,
    favouritesInteractor: FavouritesInteractor,
    private val currencyInteractor: CurrencyInteractor,
) : BaseViewModel(favouritesInteractor, currencyInteractor) {

    private val _exchangeRateDataFlow =
        MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val exchangeRateDataFlow: StateFlow<ExchangeRatesUiState> = _exchangeRateDataFlow

    init {
        loadExchangeRates()
    }

    fun onRetryClicked() {
        loadExchangeRates()
    }

    private fun loadExchangeRates() {
        viewModelScope.launch {
            currencyInteractor.observeBaseCurrency(this)
                .collect {
                    exchangeRateListInteractor.getExchangeRateList(it)
                        .flowOn(Dispatchers.IO)
                        .onEach { dataResult ->
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
                        .collect { result ->
                            _exchangeRateDataFlow.update {
                                when (result) {
                                    is Loading -> ExchangeRatesUiState.Loading
                                    is Success -> ExchangeRatesUiState.Success(result.data)
                                    is Error -> ExchangeRatesUiState.Error(result.exception)
                                }
                            }
                        }
                }
        }
    }
}