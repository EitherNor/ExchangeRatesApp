package com.aeon.exchangeratesapp.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(private val currencyInteractor: CurrencyInteractor) :
    ViewModel() {

    private val _currencyDataFlow = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Loading)
    val currencyDataFlow: StateFlow<CurrencyUiState> = _currencyDataFlow

    init {
        observeCurrencyCodes()
    }

    fun onCurrencyCodeClicked(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyInteractor.updateBaseCurrency(code)
        }
    }

    private fun observeCurrencyCodes() {
        viewModelScope.launch {
            currencyInteractor.observeCurrencyCodes(this)
                .collect { currencyCodes ->
                    if (currencyCodes.isNotEmpty()) {
                        _currencyDataFlow.update {
                            CurrencyUiState.Success(currencyCodes.sortedBy { it })
                        }
                    }
                }
        }
    }
}
