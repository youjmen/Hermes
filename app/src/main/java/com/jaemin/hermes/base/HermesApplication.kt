package com.jaemin.hermes.base

import android.app.Application
import com.jaemin.hermes.di.bookModule
import com.jaemin.hermes.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HermesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@HermesApplication)
            modules(listOf(networkModule, bookModule))
        }
    }
}