package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("road_address")
    val roadAddress: RoadAddressResponse)