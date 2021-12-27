package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.remote.LocationDataSource
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.exception.EmptyPlaceException
import com.jaemin.hermes.response.AddressesResponse
import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Completable
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

    override fun searchPlaceByAddress(
        longitude: Double,
        latitude: Double
    ): Single<AddressesResponse> =
        locationDataSource.searchPlaceByAddress(longitude, latitude)
            .map { if(it.addresses.isNullOrEmpty()){
                throw EmptyPlaceException()
            }
            else{
                it
            }}

    override fun insertCurrentLocation(place: Place): Completable =
        locationDataSource.insertCurrentLocation(place)

    override fun getCurrentLocation(): Single<Place> =
        locationDataSource.getCurrentLocation()


}