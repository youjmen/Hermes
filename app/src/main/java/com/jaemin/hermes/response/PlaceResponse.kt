package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place

data class PlaceResponse(
    @SerializedName("place_name")
    val name : String,
    @SerializedName("road_address_name")
    val roadAddress : String,
    @SerializedName("y")
    val latitude : String,
    @SerializedName("x")
    val longitude : String,
    @SerializedName("phone")
    val phoneNumber : String
)

fun PlaceResponse.toEntity() : Place =
    Place(name, roadAddress, latitude.toDouble(), longitude.toDouble(), phoneNumber)

fun PlaceResponse.toBookstore() : Bookstore =
    Bookstore(name, roadAddress, latitude.toDouble(), longitude.toDouble(), phoneNumber)