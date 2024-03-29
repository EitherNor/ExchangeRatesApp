package com.aeon.exchangeratesapp.ui

import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aeon.exchangeratesapp.R
import com.aeon.exchangeratesapp.ui.favourites.FavouritesFragment
import com.aeon.exchangeratesapp.ui.ratelist.RatesListFragment

class ExchangeRatesTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    enum class ExchangeRatesTab(@StringRes val titleStringRes: Int) {
        LATEST(R.string.exchangeRatesListTitle),
        FAVOURITE(R.string.favouriteRatesListTitle);

        companion object {
            fun fromPosition(position: Int): ExchangeRatesTab {
                return values().first { it.ordinal == position }
            }
        }
    }

    override fun createFragment(position: Int) =
        when (position) {
            ExchangeRatesTab.LATEST.ordinal -> RatesListFragment()
            ExchangeRatesTab.FAVOURITE.ordinal -> FavouritesFragment()
            else -> RatesListFragment()
        }

    override fun getItemCount() = ExchangeRatesTab.values().size
}
