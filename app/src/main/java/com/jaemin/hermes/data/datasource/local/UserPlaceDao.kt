package com.jaemin.hermes.data.datasource.local

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface UserPlaceDao{
    @Query("SELECT * FROM user_place")
    fun getUserPlace() : Single<List<UserPlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserPlace(userPlace: UserPlace) : Single<Unit>

    @Query("DELETE FROM user_place")
    fun deleteAll() : Single<Unit>
}