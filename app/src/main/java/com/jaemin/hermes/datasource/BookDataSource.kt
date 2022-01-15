package com.jaemin.hermes.datasource

import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

interface BookDataSource {
    fun searchBooks(bookName : String) : Single<BooksResponse>

    fun getBookInformation(isbn : String) : Single<BooksResponse>

    fun getKyoboBookStocks(isbn : String, bookstores : List<Bookstore>) : Single<Unit>

    fun getBestSellers() : Single<BooksResponse>

    fun getNewSpecialBooks() : Single<BooksResponse>

    fun getNewBooks() : Single<BooksResponse>

}