package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.data.NetworkResultMapper.tryAsNetworkResult
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import com.aeon.exchangeratesapp.domain.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ExchangeRateRepository @Inject constructor() : IExchangeRateRepository {

    override fun getExchangeRateData(): Flow<NetworkResult<List<ExchangeRateDto>>> {
        return flow {
            throw IOException("NO INTERNET TEST!")
            emit(
                listOf(
                    ExchangeRateDto("EUR", "100.23", false),
                    ExchangeRateDto("USD", "44.00", true),
                    ExchangeRateDto("TRY", "1.55", true),
                    ExchangeRateDto("AMD", "0.58", false),
                    ExchangeRateDto("EUR", "100.23", false),
                    ExchangeRateDto("USD", "44.00", true),
                    ExchangeRateDto("TRY", "1.55", true),
                    ExchangeRateDto("AMD", "0.58", false),
                    ExchangeRateDto("EUR", "100.23", false),
                    ExchangeRateDto("USD", "44.00", true),
                    ExchangeRateDto("TRY", "1.55", true),
                    ExchangeRateDto("AMD", "0.58", false),
                    ExchangeRateDto("EUR", "100.23", false),
                    ExchangeRateDto("USD", "44.00", true),
                    ExchangeRateDto("TRY", "1.55", true),
                    ExchangeRateDto("AMD", "0.58", false),
                    ExchangeRateDto("EUR", "100.23", false),
                    ExchangeRateDto("USD", "44.00", true),
                    ExchangeRateDto("TRY", "1.55", true),
                    ExchangeRateDto("AMD", "0.58", false),
                )
            )
        }
            .tryAsNetworkResult()
    }
}