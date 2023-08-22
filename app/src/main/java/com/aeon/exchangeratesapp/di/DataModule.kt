package com.aeon.exchangeratesapp.di

import android.content.Context
import com.aeon.exchangeratesapp.data.CurrencyRepositoryImpl
import com.aeon.exchangeratesapp.data.ExchangeRateRepositoryImpl
import com.aeon.exchangeratesapp.data.FavouritesRepositoryImpl
import com.aeon.exchangeratesapp.data.SortRepositoryImpl
import com.aeon.exchangeratesapp.data.api.ExchangeRatesApi
import com.aeon.exchangeratesapp.data.db.ExchangeRateDatabase
import com.aeon.exchangeratesapp.domain.currency.CurrencyRepository
import com.aeon.exchangeratesapp.domain.favourites.FavouritesRepository
import com.aeon.exchangeratesapp.domain.ratelist.ExchangeRateRepository
import com.aeon.exchangeratesapp.domain.sort.SortRepository
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

        @Provides
        @Singleton
        fun provideFavouritesRepository(appContext: Context): FavouritesRepository {
            return FavouritesRepositoryImpl(ExchangeRateDatabase.getInstance(appContext).exchangeRateDao())
        }
    }

    @Singleton
    @Binds
    fun ExchangeRateRepositoryImpl.bindExchangeRateRepository(): ExchangeRateRepository

    @Singleton
    @Binds
    fun CurrencyRepositoryImpl.bindCurrencyRepository(): CurrencyRepository

    @Binds
    @Singleton
    fun SortRepositoryImpl.bindSortRepository(): SortRepository
}