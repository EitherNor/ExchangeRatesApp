package com.aeon.exchangeratesapp.ui.sort

sealed interface SortModeUiState {
    object Loading : SortModeUiState
    data class Success(val sortMode: SortMode): SortModeUiState
}
