package com.jaemin.hermes.datasource.remote

import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("ItemSearch.aspx?QueryType=Title&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBooks(
        @Query("ttbkey") ttbKey: String,
        @Query("Query") bookName: String
    ): Single<BooksResponse>

    @GET("ItemSearch.aspx?QueryType=Title&MaxResults=20&SearchTarget=Book&output=js&Version=20131101")
    fun searchBooks(
        @Query("ttbkey") ttbKey: String,
        @Query("Query") bookName: String,
        @Query("start") page: Int
    ): Single<BooksResponse>

    @GET("ItemLookUp.aspx?itemIdType=ISBN13&output=js")
    fun getBookInformation(
        @Query("ttbkey") ttbKey: String,
        @Query("ItemId") itemId: String
    ): Single<BooksResponse>

    @GET("ItemLookUp.aspx?output=js")
    fun getBookInformationWithISBN(
        @Query("ttbkey") ttbKey: String,
        @Query("ItemId") itemId: String
    ): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=BestSeller&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getBestSellers(@Query("ttbkey") ttbKey: String): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=BestSeller&MaxResults=20&SearchTarget=Book&output=js&Version=20131101")
    fun getBestSellers(
        @Query("ttbkey") ttbKey: String,
        @Query("start") page: Int
    ): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=ItemNewSpecial&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getNewSpecialBooks(@Query("ttbkey") ttbKey: String): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=ItemNewSpecial&MaxResults=20&SearchTarget=Book&output=js&Version=20131101")
    fun getNewSpecialBooks(
        @Query("ttbkey") ttbKey: String, @Query("start") page: Int
    ): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=ItemNewAll&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getNewBooks(@Query("ttbkey") ttbKey: String): Single<BooksResponse>

    @GET("ItemList.aspx?QueryType=ItemNewAll&MaxResults=20&SearchTarget=Book&output=js&Version=20131101")
    fun getNewBooks(
        @Query("ttbkey") ttbKey: String,
        @Query("start") page: Int
    ): Single<BooksResponse>

}