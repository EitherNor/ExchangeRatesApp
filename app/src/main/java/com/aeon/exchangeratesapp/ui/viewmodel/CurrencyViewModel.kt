package com.aeon.exchangeratesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import com.aeon.exchangeratesapp.ui.state.CurrencyUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(private val currencyRepository: ICurrencyRepository) :
    ViewModel() {

    private val _currencyDataFlow = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Loading)
    val currencyDataFlow: StateFlow<CurrencyUiState> = _currencyDataFlow

    init {
        observeCurrencyCodes()
    }

    fun onCurrencyCodeClicked(code: String) {
        Log.d("QWE", "Code clicked: $code")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                currencyRepository.updateBaseCurrency(code)
            }
        }
    }

    private fun observeCurrencyCodes() {
        viewModelScope.launch {
            currencyRepository.observeCurrencyCodes(this)
                .collect { currencyCodes ->
                    if (currencyCodes.isNotEmpty()) {
                        _currencyDataFlow.update {
                            CurrencyUiState.Success(currencyCodes)
                        }
                    }
                }
        }
    }
}