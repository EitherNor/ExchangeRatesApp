package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractorImpl
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractorImpl
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateListInteractorImpl
import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateListInteractor
import com.aeon.exchangeratesapp.domain.sort.SortInteractor
import com.aeon.exchangeratesapp.domain.sort.SortInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun ExchangeRateListInteractorImpl.bindExchangeRateListInteractor(): ExchangeRateListInteractor

    @Binds
    fun FavouritesInteractorImpl.bindFavouritesInteractor(): FavouritesInteractor

    @Binds
    fun CurrencyInteractorImpl.bindCurrencyInteractor(): CurrencyInteractor

    @Binds
    fun SortInteractorImpl.bindSortInteractor(): SortInteractor
}
