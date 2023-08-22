package com.aeon.exchangeratesapp.domain.ratelist

import com.aeon.exchangeratesapp.domain.DataResult
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateListInteractorImpl @Inject constructor(private val exchangeRateRepository: ExchangeRateRepository) :
    ExchangeRateListInteractor {

    companion object {
        private val POLLING_RATE_INTERVAL_MILLIS = TimeUnit.MINUTES.toMillis(1)
    }

    override fun getExchangeRateList(baseCurrency: String): Flow<DataResult<ExchangeRateDtoResult>> {
        val timer = (0..Long.MAX_VALUE)
            .asSequence()
            .asFlow()
            .flatMapMerge {
                if (it > 0) {
                    delay(POLLING_RATE_INTERVAL_MILLIS)
                }
                exchangeRateRepository.getExchangeRateData(baseCurrency, it > 0)
            }
        return timer
    }
}
