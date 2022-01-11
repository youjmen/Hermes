package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.LocationDataSource
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) : LocationRepository{
    override fun searchPlaces(query: String): Single<List<Place>> =
        locationDataSource.searchPlaces(query).map { placesResponse ->
            placesResponse.places.map { placeResponse -> placeResponse.toEntity() } }

    override fun searchNearbyPlaces(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<List<Place>> =
        locationDataSource.searchNearbyPlaces(query, longitude, latitude).map  { placesResponse ->
            placesResponse.places.map { placeResponse -> placeResponse.toEntity() } }

    override fun searchPlaceByAddress(
        longitude: Double,
        latitude: Double
    ): Single<Place> =
        locationDataSource.searchPlaceByAddress(longitude, latitude)
            .map {
                if(it.addresses.first().roadAddress == null){
                    Place(it.addresses.first().lotNumberAddress!!.addressName, it.addresses.first().lotNumberAddress!!.addressName, latitude, longitude)
                }
                else{
                    Place(it.addresses.first().roadAddress!!.addressName, it.addresses.first().roadAddress!!.addressName, latitude, longitude)

                }
            }

    override fun insertCurrentLocation(place: Place): Completable =
        locationDataSource.insertCurrentLocation(place)

    override fun getCurrentLocation(): Single<Place> =
        locationDataSource.getCurrentLocation()


}