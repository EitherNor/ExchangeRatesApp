package com.aeon.exchangeratesapp.data

import android.content.Context
import android.content.SharedPreferences
import com.aeon.exchangeratesapp.domain.currency.CurrencyRepository
import com.aeon.exchangeratesapp.extensions.StateFlowExtensions.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(appContext: Context) : CurrencyRepository {

    companion object {
        private const val CURRENCY_PREFERENCES_FILE_NAME = "currencyPreferences"
        private const val SET_CURRENCY_CODES = "SET_CURRENCY_CODES"
        private const val STRING_BASE_CURRENCY = "STRING_BASE_CURRENCY"
        private const val DEFAULT_BASE_CURRENCY = "EUR"
    }

    private val _currencyDataFlow = MutableStateFlow<Set<String>>(setOf())
    private val _baseCurrencyFlow = MutableStateFlow(DEFAULT_BASE_CURRENCY)

    private val editor =
        appContext.getSharedPreferences(CURRENCY_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE).edit()
    private var preferences: SharedPreferences =
        appContext.getSharedPreferences(CURRENCY_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    override suspend fun updateCurrencyCodes(currencyCodeSet: Set<String>) {
        editor.apply {
            putStringSet(SET_CURRENCY_CODES, currencyCodeSet)
            commit()
        }
        _currencyDataFlow.update { currencyCodeSet }
    }

    override suspend fun updateBaseCurrency(baseCurrency: String) {
        editor.apply {
            putString(STRING_BASE_CURRENCY, baseCurrency)
            commit()
        }
        _baseCurrencyFlow.update { baseCurrency }
    }

    override fun observeCurrencyCodes(coroutineScope: CoroutineScope): Flow<Set<String>> {
        return _currencyDataFlow.map(coroutineScope) {
            it.ifEmpty { preferences.getStringSet(SET_CURRENCY_CODES, emptySet()) ?: emptySet() }
        }
    }

    override fun observeBaseCurrency(coroutineScope: CoroutineScope): Flow<String> {
        return _baseCurrencyFlow.map(coroutineScope) {
            if (it == DEFAULT_BASE_CURRENCY)
                preferences.getString(STRING_BASE_CURRENCY, DEFAULT_BASE_CURRENCY)
                    ?: DEFAULT_BASE_CURRENCY
            else it
        }
    }
}