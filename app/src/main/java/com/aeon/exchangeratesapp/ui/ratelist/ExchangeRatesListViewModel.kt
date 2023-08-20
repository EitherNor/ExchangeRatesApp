package com.aeon.exchangeratesapp.ui.ratelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.DataResult.Error
import com.aeon.exchangeratesapp.domain.DataResult.Loading
import com.aeon.exchangeratesapp.domain.DataResult.Success
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import com.aeon.exchangeratesapp.domain.ratelist.IExchangeRateListInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExchangeRatesListViewModel @Inject constructor(
    private val exchangeRateListInteractor: IExchangeRateListInteractor,
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
            currencyRepository.observeBaseCurrency(this)
                .collect {
                    exchangeRateListInteractor.getExchangeRateList(it)
                        .flowOn(Dispatchers.IO)
                        .onEach { dataResult ->
                            if (dataResult is Success) {
                                dataResult.data.let {
                                    currencyRepository.updateCurrencyCodes(dataResult.data.exchangeRateDtoList.map { it.currencyCode }
                                        .toSet())
                                }
                            }
                        }
                        .collect { result ->
                            _exchangeRateDataFlow.update {
                                when (result) {
                                    is Loading -> ExchangeRatesUiState.Loading
                                    is Success -> ExchangeRatesUiState.Success(result.data.exchangeRateDtoList)
                                    is Error -> ExchangeRatesUiState.Error(result.exception)
                                }
                            }
                        }
                }
        }
    }
}