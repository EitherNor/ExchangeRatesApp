package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.domain.DataResult
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.ExchangeRateDtoResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException
import java.lang.IllegalStateException

object NetworkResultMapper {
    private const val RETRY_TIME_IN_MILLIS = 1_000L
    private const val RETRY_ATTEMPT_COUNT = 3

    fun Flow<ExchangeRatesData>.tryAsDataResult(isScheduled: Boolean): Flow<DataResult<ExchangeRateDtoResult>> {
        return this
            .map { data ->
                return@map if (data.error != null || data.rates == null || data.base == null) {
                    data.error?.let { DataResult.Error(IllegalStateException(it.type)) }
                        ?: DataResult.Error(IllegalStateException("No data received"))
                } else {
                    DataResult.Success(
                        ExchangeRateDtoResult(
                            data.rates.map {
                                ExchangeRateDto(
                                    baseCurrency = data.base,
                                    currencyCode = it.key,
                                    exchangeRateValue = it.value,
                                    isFavourite = false
                                )
                            })
                    )
                }
            }
            .onStart {
                if (!isScheduled) {
                    emit(DataResult.Loading)
                }
            }
            .retryWhen { cause, attempt ->
                if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }
            .catch { emit(DataResult.Error(it)) }
    }
}
