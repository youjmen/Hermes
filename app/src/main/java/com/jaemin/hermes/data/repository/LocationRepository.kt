package com.jaemin.hermes.data.repository

import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocationRepository {
    fun searchPlaces(query : String) : Single<List<Place>>

    fun searchNearbyPlaces(query : String, longitude : Double, latitude : Double) : Single<List<Place>>

    fun searchPlaceByAddress(longitude : Double, latitude : Double) : Single<Place>

    fun insertCurrentLocation(place : Place) : Completable

    fun getCurrentLocation() : Single<Place>

    fun searchBookstoreByAddressWithRadius(longitude : Double, latitude : Double, radius : Int) : Single<List<Bookstore>>

}