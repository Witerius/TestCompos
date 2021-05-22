package com.example.testcompose

import android.app.Application
import com.example.testcompose.network.networkModule
import com.example.testcompose.ui.di.repositories
import com.example.testcompose.ui.di.useCase
import com.example.testcompose.ui.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            fileProperties()
            androidLogger()
            androidContext(applicationContext)
            modules(networkModule)
            modules(useCase)
            modules(repositories)
            modules(viewModule)
        }
    }
}