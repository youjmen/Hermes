package com.jaemin.hermes.data.datasource

import androidx.paging.PagingData
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.data.response.BookResponse
import com.jaemin.hermes.data.response.BooksResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface BookDataSource {
    fun searchBooks(bookName : String) : Single<BooksResponse>

    fun searchBooksWithPaging(bookName : String) : Observable<PagingData<BookResponse>>

    fun getBookInformation(isbn : String) : Single<BooksResponse>

    fun getKyoboBookStocks(isbn : String, bookstores : List<Bookstore>) : Single<List<Bookstore>>

    fun getBestSellers() : Single<BooksResponse>

    fun getBestSellersWithPaging() : Observable<PagingData<BookResponse>>

    fun getNewSpecialBooks() : Single<BooksResponse>

    fun getNewSpecialBooksWithPaging() : Observable<PagingData<BookResponse>>

    fun getNewBooks() : Single<BooksResponse>

    fun getNewBooksWithPaging() : Observable<PagingData<BookResponse>>
}