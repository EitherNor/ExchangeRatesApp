package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.data.NetworkResultMapper.tryAsNetworkResult
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import com.aeon.exchangeratesapp.domain.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor() : IExchangeRateRepository {

    override fun getExchangeRateData(): Flow<NetworkResult<ExchangeRatesResponse>> {
        return flow {
//            throw IOException("NO INTERNET TEST!")
            kotlinx.coroutines.delay(5000L)
            emit(
                ExchangeRatesResponse.stub()
            )
        }
            .tryAsNetworkResult()
    }
}