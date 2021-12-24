package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.LocationDataSource
import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Single

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) : LocationRepository{
    override fun searchPlaces(query: String): Single<PlacesResponse> =
        locationDataSource.searchPlaces(query)

    override fun searchNearbyPlaces(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<PlacesResponse> =
        locationDataSource.searchNearbyPlaces(query, longitude, latitude)


}