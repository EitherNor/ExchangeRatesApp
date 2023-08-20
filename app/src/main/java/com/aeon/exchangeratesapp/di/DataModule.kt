package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.data.CurrencyRepository
import com.aeon.exchangeratesapp.data.ExchangeRateRepository
import com.aeon.exchangeratesapp.data.api.ExchangeRatesApi
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface DataModule {

    companion object {
        private const val BASE_URL = "http://api.exchangeratesapi.io/"

        @Provides
        fun provide(): ExchangeRatesApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ExchangeRatesApi::class.java)
        }
    }

    @Singleton
    @Binds
    fun ExchangeRateRepository.bindExchangeRateRepository(): IExchangeRateRepository

    @Singleton
    @Binds
    fun CurrencyRepository.bindCurrencyRepository(): ICurrencyRepository
}