package com.jaemin.hermes.datasource.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserPlace::class], version = 2)
abstract class UserPlaceDatabase : RoomDatabase(){
    abstract fun userPlaceDao() : UserPlaceDao
}