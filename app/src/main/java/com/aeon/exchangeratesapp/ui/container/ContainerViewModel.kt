package com.aeon.exchangeratesapp.ui.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import javax.inject.Inject

class ContainerViewModel @Inject constructor(private val currencyInteractor: CurrencyInteractor) :
    ViewModel() {

    fun observeBaseCurrency() = currencyInteractor.observeBaseCurrency(viewModelScope)
}