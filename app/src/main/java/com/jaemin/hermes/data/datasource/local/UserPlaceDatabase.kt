package com.jaemin.hermes.data.datasource.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserPlace::class], version = 2)
abstract class UserPlaceDatabase : RoomDatabase(){
    abstract fun userPlaceDao() : UserPlaceDao
}