package com.aeon.exchangeratesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.NetworkResult.Error
import com.aeon.exchangeratesapp.domain.NetworkResult.Loading
import com.aeon.exchangeratesapp.domain.NetworkResult.Success
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import com.aeon.exchangeratesapp.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExchangeRatesListViewModel @Inject constructor(
    private val exchangeRateRepository: IExchangeRateRepository,
) : ViewModel() {

    private val _exchangeRateDataFlow = MutableStateFlow<UiState>(UiState.Loading)
    val exchangeRateDataFlow: StateFlow<UiState> = _exchangeRateDataFlow

    init {
        loadExchangeRates()
    }

    fun onRetryClicked() {
        loadExchangeRates()
    }

    private fun loadExchangeRates() {
        viewModelScope.launch {
            exchangeRateRepository.getExchangeRateData()
                .collect { result ->
                    _exchangeRateDataFlow.update {
                        when (result) {
                            is Loading -> UiState.Loading
                            is Success -> UiState.Success(result.data)
                            is Error -> UiState.Error(result.exception)
                        }
                    }
                }
        }
    }
}