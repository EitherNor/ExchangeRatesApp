package com.aeon.exchangeratesapp.ui.base

import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult

sealed interface ExchangeRatesUiState {
    object Loading : ExchangeRatesUiState
    data class Success(val data: ExchangeRateDtoResult) : ExchangeRatesUiState
    data class Error(val throwable: Throwable? = null) : ExchangeRatesUiState
}