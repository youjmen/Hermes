package com.jaemin.hermes.repository

import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Single

interface LocationRepository {
    fun searchPlaces(query : String) : Single<PlacesResponse>

    fun searchNearbyPlaces(query : String, longitude : Double, latitude : Double) : Single<PlacesResponse>
}