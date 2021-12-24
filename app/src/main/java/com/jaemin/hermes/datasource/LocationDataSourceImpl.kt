package com.jaemin.hermes.datasource

import com.jaemin.hermes.remote.LocationService
import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Single

class LocationDataSourceImpl(private val locationService: LocationService)  : LocationDataSource{
    override fun searchPlaces(query: String): Single<PlacesResponse> =
        locationService.searchPlace(query)

    override fun searchNearbyPlaces(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<PlacesResponse> =
        locationService.searchNearbyPlace(query, longitude.toString(), latitude.toString())
}