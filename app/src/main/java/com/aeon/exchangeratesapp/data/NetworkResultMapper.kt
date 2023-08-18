package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.domain.NetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

object NetworkResultMapper {
    private const val RETRY_TIME_IN_MILLIS = 1_000L
    private const val RETRY_ATTEMPT_COUNT = 3

    fun <T> Flow<T>.tryAsNetworkResult(): Flow<NetworkResult<T>> {
        return this
            .map<T, NetworkResult<T>> {
                NetworkResult.Success(it)
            }
            .onStart { emit(NetworkResult.Loading) }
            .retryWhen { cause, attempt ->
                if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }
            .catch { emit(NetworkResult.Error(it)) }
    }
}