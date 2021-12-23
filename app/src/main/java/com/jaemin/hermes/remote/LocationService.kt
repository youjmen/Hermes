package com.jaemin.hermes.remote

import com.jaemin.hermes.response.LocationsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("/v2/local/search/keyword.json")
    fun searchLocation(@Query("query") query : String) : Single<LocationsResponse>

    @GET("/v2/local/search/keyword.json")
    fun searchNearbyLocation(@Query("query") query : String,@Query("x") longitude : String,@Query("y") latitude : String) : Single<LocationsResponse>
}