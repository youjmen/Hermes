package com.jaemin.hermes.repository

import com.jaemin.hermes.remote.BookService
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

class BookRepositoryImpl(private val bookService: BookService) : BookRepository{
    override fun searchBooks(bookName: String): Single<BooksResponse> {
        return bookService.searchBooks(bookName)
    }

}