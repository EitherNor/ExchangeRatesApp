package com.aeon.exchangeratesapp.domain.favourites

import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import kotlinx.coroutines.flow.Flow

interface FavouritesInteractor {

    fun observeFavourites(baseCurrency: String): Flow<List<ExchangeRateDto>>

    suspend fun toggleFavourite(exchangeRateDto: ExchangeRateDto)

    suspend fun updateAllFavouritesValues(exchangeRateDtoList: List<ExchangeRateDto>)
}
