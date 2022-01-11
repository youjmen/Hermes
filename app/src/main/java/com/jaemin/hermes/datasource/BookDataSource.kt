package com.jaemin.hermes.datasource

import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

interface BookDataSource {
    fun searchBooks(bookName : String) : Single<BooksResponse>

    fun getBookInformation(isbn : String) : Single<BooksResponse>

}