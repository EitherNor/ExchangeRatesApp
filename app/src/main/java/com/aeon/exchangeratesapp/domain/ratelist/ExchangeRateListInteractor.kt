package com.aeon.exchangeratesapp.domain.ratelist

import com.aeon.exchangeratesapp.BuildConfig
import com.aeon.exchangeratesapp.domain.IExchangeRateRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateListInteractor @Inject constructor(private val exchangeRateRepository: IExchangeRateRepository) :
    IExchangeRateListInteractor {

    override fun getExchangeRateList(baseCurrency: String) =
        exchangeRateRepository.getExchangeRateData(baseCurrency)
}