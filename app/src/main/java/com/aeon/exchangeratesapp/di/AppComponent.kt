package com.aeon.exchangeratesapp.di

import com.aeon.exchangeratesapp.ui.fragment.RatesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class
    ]
)
interface AppComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun inject(fragment: RatesListFragment)
}