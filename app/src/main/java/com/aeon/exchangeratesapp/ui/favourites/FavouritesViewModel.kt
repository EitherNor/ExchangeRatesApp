package com.aeon.exchangeratesapp.ui.favourites

import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.ui.base.BaseViewModel
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    favouritesInteractor: FavouritesInteractor,
    currencyInteractor: CurrencyInteractor,
) :
    BaseViewModel(favouritesInteractor, currencyInteractor) {
    init {
        observeFavourites()
    }
}