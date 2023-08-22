package com.aeon.exchangeratesapp.ui.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.sort.SortInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SortViewModel @Inject constructor(private val sortInteractor: SortInteractor) : ViewModel() {

    private val _sortModeFlow = MutableStateFlow<SortModeUiState>(SortModeUiState.Loading)
    val sortModeFlow = _sortModeFlow

    init {
        observeSortMode()
    }

    fun onSortClicked(sortMode: SortMode) {
        viewModelScope.launch {
            sortInteractor.updateSortMode(sortMode)
        }
    }

    private fun observeSortMode() {
        viewModelScope.launch {
            sortInteractor.observeSortMode(this)
                .collect { sortMode ->
                    _sortModeFlow.update {
                        SortModeUiState.Success(sortMode)
                    }
                }
        }
    }
}
