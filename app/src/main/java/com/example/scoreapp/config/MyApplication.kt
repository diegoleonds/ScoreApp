package com.example.scoreapp.config

import android.app.Application
import com.example.scoreapp.data.di.daoModule
import com.example.scoreapp.data.di.databaseModule
import com.example.scoreapp.data.di.repositoryModule
import com.example.scoreapp.domain.di.useCaseModule
import com.example.scoreapp.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    /**
     * Base Application class for the app
     * Start koin for dependency injection
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@MyApplication)
            modules(databaseModule, daoModule, useCaseModule, viewModelModule, repositoryModule)
        }
    }

}