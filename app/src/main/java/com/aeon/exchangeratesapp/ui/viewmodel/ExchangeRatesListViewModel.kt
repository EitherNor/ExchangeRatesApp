package com.aeon.exchangeratesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import com.aeon.exchangeratesapp.domain.NetworkResult.Error
import com.aeon.exchangeratesapp.domain.NetworkResult.Loading
import com.aeon.exchangeratesapp.domain.NetworkResult.Success
import com.aeon.exchangeratesapp.ui.state.ExchangeRatesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class ExchangeRatesListViewModel @Inject constructor(
    private val exchangeRateRepository: IExchangeRateRepository,
    private val currencyRepository: ICurrencyRepository,
) : ViewModel() {

    private val _exchangeRateDataFlow =
        MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val exchangeRateDataFlow: StateFlow<ExchangeRatesUiState> = _exchangeRateDataFlow

    init {
        loadExchangeRates()
    }

    fun onRetryClicked() {
        loadExchangeRates()
    }

    private fun loadExchangeRates() {
        viewModelScope.launch {
            exchangeRateRepository.getExchangeRateData()
                .onEach {
                    if (it is Success) {
                        currencyRepository.updateCurrencyCodes(it.data.rates.keys)
                    }
                }
                .collect { result ->
                    _exchangeRateDataFlow.update {
                        when (result) {
                            is Loading -> ExchangeRatesUiState.Loading
                            is Success -> ExchangeRatesUiState.Success(result.data.rates.entries.map {
                                ExchangeRateDto(
                                    it.key,
                                    it.value.toString(),
                                    true
                                )
                            })

                            is Error -> ExchangeRatesUiState.Error(result.exception)
                        }
                    }
                }
        }
    }
}