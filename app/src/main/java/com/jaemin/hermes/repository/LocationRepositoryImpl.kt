package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.LocationDataSource
import com.jaemin.hermes.response.LocationsResponse
import io.reactivex.rxjava3.core.Single

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) : LocationRepository{
    override fun searchBuildings(query: String): Single<LocationsResponse> =
        locationDataSource.searchBuildings(query)

    override fun searchNearbyBuildings(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<LocationsResponse> =
        locationDataSource.searchNearbyBuildings(query, longitude, latitude)


}