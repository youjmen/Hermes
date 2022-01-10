package com.jaemin.hermes.base

import android.app.Application
import com.jaemin.hermes.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HermesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@HermesApplication)
            modules(listOf(dbModule, networkModule, bookModule, locationModule, mainModule, bookstoreModule))
        }
    }
}