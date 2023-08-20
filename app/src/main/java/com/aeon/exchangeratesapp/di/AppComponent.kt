package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.ui.container.ContainerFragment
import com.aeon.exchangeratesapp.ui.currency.CurrencySelectorDialogFragment
import com.aeon.exchangeratesapp.ui.ratelist.RatesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class,
        ContextModule::class,
        DomainModule::class,
    ]
)
interface AppComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun inject(fragment: RatesListFragment)

    fun inject(fragment: CurrencySelectorDialogFragment)

    fun inject(fragment: ContainerFragment)
}