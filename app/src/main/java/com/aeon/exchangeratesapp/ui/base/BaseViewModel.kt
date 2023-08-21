package com.aeon.exchangeratesapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected val favouritesInteractor: FavouritesInteractor,
    private val currencyInteractor: CurrencyInteractor,
) :
    ViewModel() {

    private val _favouritesFlow =
        MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val favouritesFlow: StateFlow<ExchangeRatesUiState> = _favouritesFlow

    fun onFavouriteClicked(exchangeRateDto: ExchangeRateDto) =
        viewModelScope.launch { favouritesInteractor.toggleFavourite(exchangeRateDto) }

    protected fun observeFavourites() {
        viewModelScope.launch {
            currencyInteractor.observeBaseCurrency(this)
                .flatMapMerge {
                    _favouritesFlow.emit(ExchangeRatesUiState.Loading)
                    favouritesInteractor.observeFavourites(it)
                }
                .collect {
                    _favouritesFlow.emit(ExchangeRatesUiState.Success(ExchangeRateDtoResult(it)))
                }
        }
    }
}