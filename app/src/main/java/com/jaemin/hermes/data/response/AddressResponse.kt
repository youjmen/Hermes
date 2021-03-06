package com.jaemin.hermes.data.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("road_address")
    val roadAddress: RoadAddressResponse?,
    @SerializedName("address")
    val lotNumberAddress: LotNumberAddressResponse?)