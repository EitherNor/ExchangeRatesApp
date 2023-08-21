package com.aeon.exchangeratesapp.domain.ratelist

import com.aeon.exchangeratesapp.domain.DataResult
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import kotlinx.coroutines.flow.Flow

interface ExchangeRateListInteractor {

    fun getExchangeRateList(baseCurrency: String): Flow<DataResult<ExchangeRateDtoResult>>
}