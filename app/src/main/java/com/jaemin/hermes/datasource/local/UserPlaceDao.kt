package com.jaemin.hermes.datasource.local

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.DELETE

@Dao
interface UserPlaceDao{
    @Query("SELECT * FROM user_place")
    fun getUserPlace() : Single<List<UserPlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserPlace(userPlace: UserPlace) : Completable

    @Query("DELETE FROM user_place")
    fun deleteAll() : Completable
}