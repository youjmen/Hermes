package com.jaemin.hermes.di

import android.app.Application
import androidx.room.Room
import com.jaemin.hermes.data.datasource.local.UserPlaceDao
import com.jaemin.hermes.data.datasource.local.UserPlaceDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val dbModule = module {

    fun provideUserPlaceDatabase(application: Application): UserPlaceDatabase {
        return Room.databaseBuilder(application, UserPlaceDatabase::class.java, "user_place_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    fun provideUserPlaceDao(db : UserPlaceDatabase) : UserPlaceDao{
        return db.userPlaceDao()
    }

    single { provideUserPlaceDatabase(androidApplication()) }
    single { provideUserPlaceDao(get())}
}