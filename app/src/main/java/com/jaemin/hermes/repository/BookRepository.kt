package com.jaemin.hermes.repository

import androidx.paging.PagingData
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.BookResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun searchBooks(bookName : String) : Single<List<Book>>

    fun searchBooksWithPaging(bookName : String) : Observable<PagingData<BookResponse>>

    fun getBookInformation(isbn : String) : Single<Book>

    fun getBestSellers() : Single<List<Book>>

    fun getBestSellersWithPaging() : Observable<PagingData<BookResponse>>

    fun getNewSpecialBooks() : Single<List<Book>>

    fun getNewSpecialBooksWithPaging() : Observable<PagingData<BookResponse>>

    fun getNewBooks() : Single<List<Book>>

    fun getNewBooksWithPaging() : Observable<PagingData<BookResponse>>

    fun getBookStocks(isbn : String, bookstores : List<Bookstore>) : Single<List<Bookstore>>

}