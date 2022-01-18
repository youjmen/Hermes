package com.jaemin.hermes.datasource

import android.util.Log
import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.datasource.remote.BookService
import com.jaemin.hermes.datasource.remote.KyoboBooksScraper
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(private val bookService: BookService, private val kyoboBooksScraper: KyoboBooksScraper) : BookDataSource {
    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(BuildConfig.TTB_KEY,bookName)

    override fun getBookInformation(isbn: String): Single<BooksResponse> {
        return if (isbn.length == 13) {
            bookService.getBookInformation(BuildConfig.TTB_KEY,isbn)
        } else{
            bookService.getBookInformationWithISBN(BuildConfig.TTB_KEY,isbn)
        }
    }

    override fun getKyoboBookStocks(isbn: String, bookstores : List<Bookstore>): Single<Unit> {
        return kyoboBooksScraper.scrapBookStock(isbn, bookstores)
    }

    override fun getBestSellers(): Single<BooksResponse> =
        bookService.getBestSellers(BuildConfig.TTB_KEY)

    override fun getNewSpecialBooks(): Single<BooksResponse> =
        bookService.getNewSpecialBooks(BuildConfig.TTB_KEY)

    override fun getNewBooks(): Single<BooksResponse> =
        bookService.getNewBooks(BuildConfig.TTB_KEY)


}