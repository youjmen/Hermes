package com.jaemin.hermes.datasource

import android.util.Log
import com.jaemin.hermes.datasource.remote.BookService
import com.jaemin.hermes.datasource.remote.KyoboBooksScraper
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(private val bookService: BookService, private val kyoboBooksScraper: KyoboBooksScraper) : BookDataSource {
    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(bookName)

    override fun getBookInformation(isbn: String): Single<BooksResponse> {
        return if (isbn.length == 13) {
            bookService.getBookInformation(isbn)
        } else{
            bookService.getBookInformationWithISBN(isbn)
        }
    }

    override fun getKyoboBookStocks(isbn: String, bookstores : List<Bookstore>): Single<Unit> {
        return kyoboBooksScraper.scrapBookStock(isbn, bookstores)
    }

    override fun getBestSellers(): Single<BooksResponse> =
        bookService.getBestSellers()

    override fun getNewSpecialBooks(): Single<BooksResponse> =
        bookService.getNewSpecialBooks()

    override fun getNewBooks(): Single<BooksResponse> =
        bookService.getNewBooks()


}