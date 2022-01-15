package com.jaemin.hermes.repository

import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun searchBooks(bookName : String) : Single<List<Book>>

    fun getBookInformation(isbn : String) : Single<Book>

    fun getBestSellers() : Single<List<Book>>

    fun getNewSpecialBooks() : Single<List<Book>>

    fun getNewBooks() : Single<List<Book>>

    fun getBookStocks(isbn : String, bookstores : List<Bookstore>) : Single<Unit>

}