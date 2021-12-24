package com.jaemin.hermes.remote

import com.jaemin.hermes.response.PlacesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("/v2/local/search/keyword.json")
    fun searchPlace(@Query("query") query : String) : Single<PlacesResponse>

    @GET("/v2/local/search/keyword.json")
    fun searchNearbyPlace(@Query("query") query : String,@Query("x") longitude : String,@Query("y") latitude : String) : Single<PlacesResponse>
}