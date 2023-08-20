package com.aeon.exchangeratesapp.ui.ratelist

import com.aeon.exchangeratesapp.domain.ExchangeRateDto

sealed interface ExchangeRatesUiState {
    object Loading : ExchangeRatesUiState
    data class Success(val data: List<ExchangeRateDto>) : ExchangeRatesUiState
    data class Error(val throwable: Throwable? = null) : ExchangeRatesUiState
}