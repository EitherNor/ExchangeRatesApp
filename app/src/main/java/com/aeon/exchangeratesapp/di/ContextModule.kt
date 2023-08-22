package com.aeon.exchangeratesapp.di

import android.content.Context
import com.aeon.exchangeratesapp.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val application: App) {
    @Provides
    @Singleton
    fun provide(): Context {
        return application.applicationContext
    }
}
