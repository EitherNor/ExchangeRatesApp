package com.aeon.exchangeratesapp.domain

import kotlinx.coroutines.flow.Flow

interface IExchangeRateRepository {

    fun getExchangeRateData(baseCurrency: String): Flow<DataResult<ExchangeRateDtoResult>>

}