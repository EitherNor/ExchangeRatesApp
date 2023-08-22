package com.aeon.exchangeratesapp.ui.favourites

import androidx.lifecycle.viewModelScope
import com.aeon.exchangeratesapp.domain.currency.CurrencyInteractor
import com.aeon.exchangeratesapp.domain.favourites.FavouritesInteractor
import com.aeon.exchangeratesapp.domain.sort.SortInteractor
import com.aeon.exchangeratesapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(
    favouritesInteractor: FavouritesInteractor,
    currencyInteractor: CurrencyInteractor,
    private val sortInteractor: SortInteractor,
) :
    BaseViewModel(favouritesInteractor, currencyInteractor, sortInteractor) {
    init {
        observeSortMode()
    }

    private fun observeSortMode() {
        viewModelScope.launch {
            sortInteractor.observeSortMode(this)
                .collect {
                    observeFavourites()
                }
        }
    }
}
