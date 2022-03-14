package com.jaemin.hermes.data.response

import com.google.gson.annotations.SerializedName

data class AddressesResponse(
    @SerializedName("documents")
    val addresses: List<AddressResponse>
)