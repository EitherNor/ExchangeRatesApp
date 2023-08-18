package com.aeon.exchangeratesapp.domain

data class ExchangeRateDto(
    val currencyCode: String,
    val exchangeRateValue: String,
    var isFavourite: Boolean,
)