package com.jaemin.hermes.datasource

import com.jaemin.hermes.remote.LocationService
import com.jaemin.hermes.response.LocationsResponse
import io.reactivex.rxjava3.core.Single

class LocationDataSourceImpl(private val locationService: LocationService)  : LocationDataSource{
    override fun searchBuildings(query: String): Single<LocationsResponse> =
        locationService.searchLocation(query)

    override fun searchNearbyBuildings(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<LocationsResponse> =
        locationService.searchNearbyLocation(query, longitude.toString(), latitude.toString())
}