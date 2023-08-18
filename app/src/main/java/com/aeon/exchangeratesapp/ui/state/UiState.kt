package com.aeon.exchangeratesapp.ui.state

import com.aeon.exchangeratesapp.domain.ExchangeRateDto

sealed interface UiState {
    object Loading : UiState

    data class Success(val data: List<ExchangeRateDto>) : UiState

    data class Error(val throwable: Throwable? = null) : UiState
}