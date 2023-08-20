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

    fun Flow<ExchangeRatesData>.tryAsDataResult(): Flow<DataResult<ExchangeRateDtoResult>> {
        return this
            .map { data ->
                data.error?.let { error ->
                    return@map DataResult.Error(IllegalStateException(error.type))
                } ?: kotlin.run {
                    return@map DataResult.Success(ExchangeRateDtoResult(
                        data.rates?.map { ExchangeRateDto(it.key, it.value.toString()) }
                            ?: emptyList()
                    ))
                }
            }
            .onStart { emit(DataResult.Loading) }
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