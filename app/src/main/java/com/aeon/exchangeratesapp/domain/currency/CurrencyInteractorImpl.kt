package com.aeon.exchangeratesapp.domain.currency

import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CurrencyInteractorImpl @Inject constructor(private val currencyRepository: CurrencyRepository) : CurrencyInteractor {

    override suspend fun updateCurrencyCodes(currencyCodeSet: Set<String>) =
        currencyRepository.updateCurrencyCodes(currencyCodeSet)

    override suspend fun updateBaseCurrency(baseCurrency: String) =
        currencyRepository.updateBaseCurrency(baseCurrency)

    override fun observeCurrencyCodes(coroutineScope: CoroutineScope) =
        currencyRepository.observeCurrencyCodes(coroutineScope)

    override fun observeBaseCurrency(coroutineScope: CoroutineScope) =
        currencyRepository.observeBaseCurrency(coroutineScope)
}