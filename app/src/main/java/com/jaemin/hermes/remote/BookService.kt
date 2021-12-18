package com.jaemin.hermes.remote

import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("ItemSearch.aspx?ttbkey=ttbyoujmen1037002&QueryType=Title&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101")
    fun searchBooks(@Query("Query") bookName : String) : Single<BooksResponse>
}