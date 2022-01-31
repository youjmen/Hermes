package com.jaemin.hermes.datasource

import android.util.Log
import com.jaemin.hermes.datasource.local.UserPlaceDao
import com.jaemin.hermes.datasource.local.toDBData
import com.jaemin.hermes.datasource.local.toEntity
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.datasource.remote.LocationService
import com.jaemin.hermes.response.AddressesResponse
import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class LocationDataSourceImpl(private val locationService: LocationService, private val userPlaceDao: UserPlaceDao)  :
    LocationDataSource {
    override fun searchPlaces(query: String): Single<PlacesResponse> =
        locationService.searchPlace(query)

    override fun searchNearbyPlaces(
        query: String,
        longitude: Double,
        latitude: Double
    ): Single<PlacesResponse> =
        locationService.searchNearbyPlace(query, longitude.toString(), latitude.toString())

    override fun searchPlaceByAddress(
        longitude: Double,
        latitude: Double
    ): Single<AddressesResponse> =
        locationService.searchPlaceByAddress(longitude.toString(), latitude.toString())


    override fun searchBookstoreByAddressWithRadius(
        longitude: Double,
        latitude: Double,
        radius: Int
    ): Single<PlacesResponse> =
        locationService.searchNearbyPlaceWithRadius("서점", longitude.toString(), latitude.toString(), radius)

    override fun insertCurrentLocation(place: Place): Completable {
        return Completable.fromSingle(userPlaceDao.deleteAll().flatMap {
            userPlaceDao.insertUserPlace(place.toDBData())
        })

    }

    override fun getCurrentLocation(): Single<Place> {
        return userPlaceDao.getUserPlace().map {
            it.last().toEntity()
        }
    }
}