package com.aeon.exchangeratesapp.domain

import kotlinx.coroutines.flow.Flow

interface IExchangeRateRepository {

    fun getExchangeRateData(): Flow<NetworkResult<List<ExchangeRateDto>>>

}