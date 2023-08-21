package com.aeon.exchangeratesapp.domain

data class ExchangeRateDtoResult(
    val exchangeRateDtoList: List<ExchangeRateDto>
)

data class ExchangeRateDto(
    val baseCurrency: String,
    val currencyCode: String,
    var exchangeRateValue: Double,
    var isFavourite: Boolean,
)