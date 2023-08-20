package com.aeon.exchangeratesapp.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow


interface ICurrencyRepository {

    suspend fun updateCurrencyCodes(currencyCodeSet: Set<String>)

    suspend fun updateBaseCurrency(baseCurrency: String)

    fun observeCurrencyCodes(coroutineScope: CoroutineScope): Flow<Set<String>>

    fun observeBaseCurrency(coroutineScope: CoroutineScope): Flow<String>
}