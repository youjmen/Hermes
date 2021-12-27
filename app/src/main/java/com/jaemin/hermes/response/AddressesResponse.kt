package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName

data class AddressesResponse(
    @SerializedName("documents")
    val addresses: List<AddressResponse>
)