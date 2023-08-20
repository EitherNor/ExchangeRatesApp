package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateListInteractor
import com.aeon.exchangeratesapp.domain.ratelist.IExchangeRateListInteractor
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun ExchangeRateListInteractor.bind(): IExchangeRateListInteractor
}