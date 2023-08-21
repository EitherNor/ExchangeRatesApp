package com.aeon.exchangeratesapp.domain.ratelist

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRateListInteractorImpl @Inject constructor(private val exchangeRateRepository: ExchangeRateRepository) :
    ExchangeRateListInteractor {

    override fun getExchangeRateList(baseCurrency: String) =
        exchangeRateRepository.getExchangeRateData(baseCurrency)
}