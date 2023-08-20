package com.aeon.exchangeratesapp.data

import com.google.gson.annotations.SerializedName

data class ExchangeRatesData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: Long?,
    @SerializedName("base") val base: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("rates") val rates: Map<String, Double>?,
    @SerializedName("error") val error: Error? = null,
)

data class Error(@SerializedName("code") val code: Int, @SerializedName("type") val type: String)