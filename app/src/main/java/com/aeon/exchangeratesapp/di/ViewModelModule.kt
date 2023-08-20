package com.aeon.exchangeratesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aeon.exchangeratesapp.ui.viewmodel.ContainerViewModel
import com.aeon.exchangeratesapp.ui.viewmodel.CurrencyViewModel
import com.aeon.exchangeratesapp.ui.viewmodel.ExchangeRatesListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRatesListViewModel::class)
    abstract fun bindExchangeRatesListViewModel(viewModel: ExchangeRatesListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    abstract fun bindCurrencyViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    abstract fun bindContainerViewModel(viewModel: ContainerViewModel): ViewModel
}