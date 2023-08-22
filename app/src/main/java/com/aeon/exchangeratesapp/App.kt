package com.aeon.exchangeratesapp

import android.app.Application
import com.aeon.exchangeratesapp.di.AppComponent
import com.aeon.exchangeratesapp.di.ContextModule
import com.aeon.exchangeratesapp.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = buildAppComponent()
    }

    private fun buildAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}
