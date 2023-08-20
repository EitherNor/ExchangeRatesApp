package com.aeon.exchangeratesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import javax.inject.Inject

class ContainerViewModel @Inject constructor(private val currencyRepository: ICurrencyRepository) :
    ViewModel() {

    fun observeBaseCurrency() = currencyRepository.observeBaseCurrency(viewModelScope)
}