package com.jaemin.hermes.repository

import com.jaemin.hermes.response.BookResponse
import com.jaemin.hermes.response.BooksResponse
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun searchBooks(bookName : String) : Single<BooksResponse>

}