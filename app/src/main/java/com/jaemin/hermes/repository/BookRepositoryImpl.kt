package com.jaemin.hermes.repository

import androidx.paging.PagingData
import com.jaemin.hermes.datasource.BookDataSource
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.BookResponse
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class BookRepositoryImpl(private val bookDataSource: BookDataSource) : BookRepository {
    override fun searchBooks(bookName: String): Single<List<Book>> =
        bookDataSource.searchBooks(bookName).map {
            it.item.map { response -> response.toEntity() }
        }

    override fun searchBooksWithPaging(bookName: String): Observable<PagingData<BookResponse>> =
        bookDataSource.searchBooksWithPaging(bookName)


    override fun getBookInformation(isbn: String): Single<Book> =
        bookDataSource.getBookInformation(isbn).map {
            it.item.first().toEntity()
        }

    override fun getBestSellers(): Single<List<Book>> =
        bookDataSource.getBestSellers().map {
            it.item.map { response -> response.toEntity() }
        }

    override fun getNewSpecialBooks(): Single<List<Book>> =
        bookDataSource.getNewSpecialBooks().map {
            it.item.map { response -> response.toEntity() }
        }

    override fun getNewSpecialBooksWithPaging(): Observable<PagingData<BookResponse>> {
        return bookDataSource.getNewSpecialBooksWithPaging()
    }

    override fun getBestSellersWithPaging(): Observable<PagingData<BookResponse>> =
        bookDataSource.getBestSellersWithPaging()


    override fun getNewBooks(): Single<List<Book>> =
        bookDataSource.getNewBooks().map {
            it.item.map { response -> response.toEntity() }
        }

    override fun getNewBooksWithPaging(): Observable<PagingData<BookResponse>> {
        return bookDataSource.getNewBooksWithPaging()
    }

    override fun getBookStocks(isbn: String, bookstores: List<Bookstore>): Single<List<Bookstore>> =
        bookDataSource.getKyoboBookStocks(isbn, bookstores)


}