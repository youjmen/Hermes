package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName
import com.jaemin.hermes.entity.Location

data class LocationResponse(
    @SerializedName("place_name")
    val name : String,
    @SerializedName("road_address_name")
    val roadAddress : String,
    @SerializedName("y")
    val latitude : String,
    @SerializedName("x")
    val longitude : String
)

fun LocationResponse.toEntity() : Location =
    Location(name, roadAddress, latitude, longitude)
