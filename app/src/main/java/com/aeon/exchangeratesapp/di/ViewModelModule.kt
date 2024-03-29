package com.aeon.exchangeratesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aeon.exchangeratesapp.ui.container.ContainerViewModel
import com.aeon.exchangeratesapp.ui.currency.CurrencyViewModel
import com.aeon.exchangeratesapp.ui.favourites.FavouritesViewModel
import com.aeon.exchangeratesapp.ui.ratelist.ExchangeRatesListViewModel
import com.aeon.exchangeratesapp.ui.sort.SortViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRatesListViewModel::class)
    fun bindExchangeRatesListViewModel(viewModel: ExchangeRatesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    fun bindContainerViewModel(viewModel: ContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesViewModel::class)
    fun bindFavouritesViewModel(viewModel: FavouritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SortViewModel::class)
    fun bindSortViewModel(viewModel: SortViewModel): ViewModel
}
