package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.data.ExchangeRateRepository
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun ExchangeRateRepository.bind(): IExchangeRateRepository
}