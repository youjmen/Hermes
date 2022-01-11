package com.jaemin.hermes.datasource

import com.jaemin.hermes.datasource.remote.BookService
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(private val bookService: BookService) : BookDataSource {
    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(bookName)

    override fun getBookInformation(isbn: String): Single<BooksResponse> =
        bookService.getBookInformation(isbn)

    override fun getBestSellers(): Single<BooksResponse> =
        bookService.getBestSellers()

    override fun getNewSpecialBooks(): Single<BooksResponse> =
        bookService.getNewSpecialBooks()

    override fun getNewBooks(): Single<BooksResponse> =
        bookService.getNewBooks()


}