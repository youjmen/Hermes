package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.remote.BookDataSource
import com.jaemin.hermes.remote.BookService
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookRepositoryImpl(private val bookDataSource: BookDataSource) : BookRepository{
    override fun searchBooks(bookName: String): Single<BooksResponse> {
        return bookDataSource.searchBooks(bookName)
    }

}