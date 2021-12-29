package com.jaemin.hermes.datasource.remote

import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.AddressesResponse
import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocationDataSource {
    fun searchPlaces(query : String) : Single<PlacesResponse>

    fun searchNearbyPlaces(query : String, longitude : Double, latitude : Double) : Single<PlacesResponse>

    fun searchPlaceByAddress(longitude : Double, latitude : Double) : Single<AddressesResponse>

    fun insertCurrentLocation(place : Place) : Completable

    fun getCurrentLocation() : Single<Place>

}