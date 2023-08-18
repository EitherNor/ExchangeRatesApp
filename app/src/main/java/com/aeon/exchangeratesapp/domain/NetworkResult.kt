package com.aeon.exchangeratesapp.domain

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Throwable? = null) : NetworkResult<Nothing>
    object Loading : NetworkResult<Nothing>
}