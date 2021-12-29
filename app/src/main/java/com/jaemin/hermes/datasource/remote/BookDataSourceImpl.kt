package com.jaemin.hermes.datasource.remote

import com.jaemin.hermes.remote.BookService
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(private val bookService: BookService) : BookDataSource {
    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(bookName)

}