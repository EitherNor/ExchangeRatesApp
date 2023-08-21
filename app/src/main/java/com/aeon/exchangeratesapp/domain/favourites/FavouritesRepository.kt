package com.aeon.exchangeratesapp.domain.favourites

import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    fun observeFavourites(baseCurrency: String): Flow<List<ExchangeRateDto>>

    suspend fun setFavourite(exchangeRateDto: ExchangeRateDto)

    suspend fun setNotFavourite(exchangeRateDto: ExchangeRateDto)

    suspend fun updateAll(exchangeRateDtoList: List<ExchangeRateDto>)
}