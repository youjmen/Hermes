package com.jaemin.hermes.datasource.remote

import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("ItemSearch.aspx?ttbkey=${BuildConfig.TTB_KEY}&QueryType=Title&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBooks(@Query("Query") bookName : String) : Single<BooksResponse>

    @GET("ItemLookUp.aspx?ttbkey=${BuildConfig.TTB_KEY}&itemIdType=ISBN13&output=js")
    fun getBookInformation(@Query("ItemId") itemId : String) : Single<BooksResponse>

    @GET("ItemLookUp.aspx?ttbkey=${BuildConfig.TTB_KEY}&output=js")
    fun getBookInformationWithISBN(@Query("ItemId") itemId : String) : Single<BooksResponse>

    @GET("ItemList.aspx?ttbkey=${BuildConfig.TTB_KEY}&QueryType=BestSeller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getBestSellers() : Single<BooksResponse>

    @GET("ItemList.aspx?ttbkey=${BuildConfig.TTB_KEY}&QueryType=ItemNewSpecial&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getNewSpecialBooks() : Single<BooksResponse>

    @GET("ItemList.aspx?ttbkey=${BuildConfig.TTB_KEY}&QueryType=ItemNewAll&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun getNewBooks() : Single<BooksResponse>
}