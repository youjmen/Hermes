package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName

data class LocationsResponse(
    @SerializedName("documents")
    val locations : List<LocationResponse>
)
