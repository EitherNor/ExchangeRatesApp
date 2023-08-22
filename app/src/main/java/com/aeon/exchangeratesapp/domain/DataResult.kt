package com.aeon.exchangeratesapp.domain

sealed interface DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>
    data class Error(val exception: Throwable? = null) : DataResult<Nothing>
    object Loading : DataResult<Nothing>
}
