package com.aeon.exchangeratesapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.domain.sort.SortInteractor
import com.aeon.exchangeratesapp.ui.sort.SortMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected val favouritesInteractor: FavouritesInteractor,
    private val currencyInteractor: CurrencyInteractor,
    private val sortInteractor: SortInteractor,
) :
    ViewModel() {

    private val _favouritesFlow =
        MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val favouritesFlow: StateFlow<ExchangeRatesUiState> = _favouritesFlow

    private var favouritesJob: Job? = null

    fun onFavouriteClicked(exchangeRateDto: ExchangeRateDto) =
        viewModelScope.launch { favouritesInteractor.toggleFavourite(exchangeRateDto) }

    protected fun observeFavourites() {
        favouritesJob?.cancel()

        favouritesJob = viewModelScope.launch {
            currencyInteractor.observeBaseCurrency(this)
                .flatMapMerge {
                    _favouritesFlow.emit(ExchangeRatesUiState.Loading)
                    favouritesInteractor.observeFavourites(it)
                }
                .map { list ->
                    applySort(list, sortInteractor.getSortMode())
                }
                .collect {
                    _favouritesFlow.emit(ExchangeRatesUiState.Success(ExchangeRateDtoResult(it)))
                }
        }
    }

    protected fun applySort(
        exchangeRateDtoList: List<ExchangeRateDto>,
        sortMode: SortMode,
    ): List<ExchangeRateDto> {
        return when(sortMode) {
            SortMode.NAME_ASC -> exchangeRateDtoList.sortedBy { it.currencyCode }
            SortMode.NAME_DESC -> exchangeRateDtoList.sortedByDescending { it.currencyCode }
            SortMode.VALUE_ASC -> exchangeRateDtoList.sortedBy { it.exchangeRateValue }
            SortMode.VALUE_DESC -> exchangeRateDtoList.sortedByDescending { it.exchangeRateValue }
        }
    }
}
