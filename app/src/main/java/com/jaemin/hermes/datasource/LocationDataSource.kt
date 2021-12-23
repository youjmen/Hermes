package com.jaemin.hermes.datasource

import com.jaemin.hermes.response.LocationsResponse
import io.reactivex.rxjava3.core.Single

interface LocationDataSource {
    fun searchBuildings(query : String) : Single<LocationsResponse>

    fun searchNearbyBuildings(query : String, longitude : Double, latitude : Double) : Single<LocationsResponse>

}