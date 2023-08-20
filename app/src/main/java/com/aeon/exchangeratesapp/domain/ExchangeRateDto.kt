package com.aeon.exchangeratesapp.domain

data class ExchangeRateDtoResult(
    val exchangeRateDtoList: List<ExchangeRateDto>
)

data class ExchangeRateDto(
    val currencyCode: String,
    val exchangeRateValue: String,
    var isFavourite: Boolean = false,
)