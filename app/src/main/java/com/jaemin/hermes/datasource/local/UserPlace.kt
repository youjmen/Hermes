package com.jaemin.hermes.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jaemin.hermes.entity.Place

@Entity(tableName = "user_place")
data class UserPlace(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "road_address")
    val roadAddress : String,
    @ColumnInfo(name = "longitude")
    val longitude : String,
    @ColumnInfo(name = "latitude")
    val latitude : String)

fun UserPlace.toEntity() : Place =
    Place(name, roadAddress, latitude.toDouble(), longitude.toDouble(), "")

fun Place.toDBData() : UserPlace =
    UserPlace(name = name, roadAddress = roadAddress, latitude = latitude.toString(), longitude = longitude.toString())