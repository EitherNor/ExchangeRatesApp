package com.aeon.exchangeratesapp.data

import com.aeon.exchangeratesapp.data.db.EntityDtoMapper.toDto
import com.aeon.exchangeratesapp.data.db.EntityDtoMapper.toEntity
import com.aeon.exchangeratesapp.data.db.EntityDtoMapper.toValueUpdatingEntity
import com.aeon.exchangeratesapp.data.db.dao.ExchangeRateDao
import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import com.aeon.exchangeratesapp.domain.favourites.FavouritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(private val dao: ExchangeRateDao) :
    FavouritesRepository {

    override fun observeFavourites(baseCurrency: String): Flow<List<ExchangeRateDto>> =
        dao.observeAllFavourites(baseCurrency).map { favourites ->
            favourites.map { it.toDto() }
        }

    override suspend fun setFavourite(exchangeRateDto: ExchangeRateDto) {
        dao.createOrUpdateExchangeRate(exchangeRateDto.toEntity())
    }

    override suspend fun setNotFavourite(exchangeRateDto: ExchangeRateDto) {
        dao.unmakeFavourite(exchangeRateDto.baseCurrency, exchangeRateDto.currencyCode)
    }

    override suspend fun updateAll(exchangeRateDtoList: List<ExchangeRateDto>) {
        dao.updateExchangeRates(exchangeRateDtoList.map { it.toValueUpdatingEntity() })
    }
}
