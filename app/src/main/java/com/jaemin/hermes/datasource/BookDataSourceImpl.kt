package com.jaemin.hermes.datasource

import com.jaemin.hermes.datasource.remote.BookService
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(private val bookService: BookService) : BookDataSource {
    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(bookName)

    override fun getBookInformation(isbn: String): Single<BooksResponse> =
        bookService.getBookInformation(isbn)

}