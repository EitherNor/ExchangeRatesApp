package com.aeon.exchangeratesapp.domain

import com.aeon.exchangeratesapp.data.ExchangeRatesResponse
import kotlinx.coroutines.flow.Flow

interface IExchangeRateRepository {

    fun getExchangeRateData(): Flow<NetworkResult<ExchangeRatesResponse>>

}