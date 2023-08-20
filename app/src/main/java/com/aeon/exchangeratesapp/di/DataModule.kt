package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.data.CurrencyRepository
import com.aeon.exchangeratesapp.data.ExchangeRateRepository
import com.aeon.exchangeratesapp.domain.ICurrencyRepository
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun ExchangeRateRepository.bindExchangeRateRepository(): IExchangeRateRepository

    @Singleton
    @Binds
    fun CurrencyRepository.bindCurrencyRepository(): ICurrencyRepository
}