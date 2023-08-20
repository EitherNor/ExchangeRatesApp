package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.ui.fragment.ContainerFragment
import com.aeon.exchangeratesapp.ui.fragment.CurrencySelectorDialogFragment
import com.aeon.exchangeratesapp.ui.fragment.RatesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class,
        ContextModule::class
    ]
)
interface AppComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun inject(fragment: RatesListFragment)

    fun inject(fragment: CurrencySelectorDialogFragment)

    fun inject(fragment: ContainerFragment)
}