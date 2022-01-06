package com.jaemin.hermes.datasource.remote

import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

interface BookDataSource {
    fun searchBooks(bookName : String) : Single<BooksResponse>

    fun getBookInformation(isbn : String) : Single<BooksResponse>

    fun getBestSellers() : Single<BooksResponse>

    fun getNewSpecialBooks() : Single<BooksResponse>

    fun getNewBooks() : Single<BooksResponse>

}