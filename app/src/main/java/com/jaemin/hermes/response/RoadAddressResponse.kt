package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName

data class RoadAddressResponse(
    @SerializedName("address_name")
    val addressName: String,
    @SerializedName("building_name")
    val buildingName: String)