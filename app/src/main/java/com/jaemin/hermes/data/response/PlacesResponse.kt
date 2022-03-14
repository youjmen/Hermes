package com.jaemin.hermes.data.response

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    @SerializedName("documents")
    val places : List<PlaceResponse>
)
