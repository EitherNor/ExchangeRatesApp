package com.aeon.exchangeratesapp.data.db

import com.aeon.exchangeratesapp.data.db.entity.ExchangeRateEntity
import com.aeon.exchangeratesapp.data.db.entity.ExchangeRateValueUpdatingEntity
import com.aeon.exchangeratesapp.domain.ExchangeRateDto

object EntityDtoMapper {

    fun ExchangeRateDto.toEntity() =
        ExchangeRateEntity(
            baseCurrency,
            currencyCode,
            exchangeRateValue,
            true
        )

    fun ExchangeRateDto.toValueUpdatingEntity() =
        ExchangeRateValueUpdatingEntity(
            baseCurrency,
            currencyCode,
            exchangeRateValue,
        )

    fun ExchangeRateEntity.toDto() =
        ExchangeRateDto(
            baseCurrency,
            comparableCurrency,
            value,
            isFavourite
        )
}
