package com.aeon.exchangeratesapp.domain.favourites

import com.aeon.exchangeratesapp.domain.ExchangeRateDto
import javax.inject.Inject

class FavouritesInteractorImpl @Inject constructor(private val favouritesRepository: FavouritesRepository) :
    FavouritesInteractor {

    override fun observeFavourites(baseCurrency: String) =
        favouritesRepository.observeFavourites(baseCurrency)

    override suspend fun toggleFavourite(exchangeRateDto: ExchangeRateDto) {
        if (!exchangeRateDto.isFavourite) {
            favouritesRepository.setFavourite(exchangeRateDto)
        } else {
            favouritesRepository.setNotFavourite(exchangeRateDto)
        }
    }

    override suspend fun updateAllFavouritesValues(exchangeRateDtoList: List<ExchangeRateDto>) {
        return favouritesRepository.updateAll(exchangeRateDtoList)
    }
}