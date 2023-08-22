package com.aeon.exchangeratesapp.ui.currency

sealed interface CurrencyUiState {
    object Loading : CurrencyUiState
    data class Success(val data: List<String>) : CurrencyUiState
    data class Error(val throwable: Throwable? = null) : CurrencyUiState
}
