package com.aeon.exchangeratesapp.ui.state

sealed interface CurrencyUiState {
    object Loading : CurrencyUiState
    data class Success(val data: Set<String>) : CurrencyUiState
    data class Error(val throwable: Throwable? = null) : CurrencyUiState
}