package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName

data class LotNumberAddressResponse(
    @SerializedName("address_name")
    val addressName: String)