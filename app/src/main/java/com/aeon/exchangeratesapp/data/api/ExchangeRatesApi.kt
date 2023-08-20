package com.aeon.exchangeratesapp.data.api

import com.aeon.exchangeratesapp.data.ExchangeRatesData
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("access_key") accessKey: String,
    ): ExchangeRatesData
}