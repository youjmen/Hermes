package com.jaemin.hermes.data.datasource

import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.data.response.AddressesResponse
import com.jaemin.hermes.data.response.PlacesResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocationDataSource {
    fun searchPlaces(query : String) : Single<PlacesResponse>

    fun searchNearbyPlaces(query : String, longitude : Double, latitude : Double) : Single<PlacesResponse>

    fun searchPlaceByAddress(longitude : Double, latitude : Double) : Single<AddressesResponse>

    fun searchBookstoreByAddressWithRadius(longitude : Double, latitude : Double, radius : Int) : Single<PlacesResponse>

    fun insertCurrentLocation(place : Place) : Completable

    fun getCurrentLocation() : Single<Place>

}