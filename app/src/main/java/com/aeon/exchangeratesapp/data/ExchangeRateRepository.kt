package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.BuildConfig
import com.aeon.exchangeratesapp.data.NetworkResultMapper.tryAsDataResult
import com.aeon.exchangeratesapp.data.api.ExchangeRatesApi
import com.aeon.exchangeratesapp.domain.DataResult
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor(private val exchangeRatesApi: ExchangeRatesApi) :
    IExchangeRateRepository {

    override fun getExchangeRateData(
        baseCurrency: String,
    ): Flow<DataResult<ExchangeRateDtoResult>> {
        return flow {
            emit(exchangeRatesApi.getExchangeRates(baseCurrency, BuildConfig.API_ACCESS_KEY))
        }
            .tryAsDataResult()
    }
}