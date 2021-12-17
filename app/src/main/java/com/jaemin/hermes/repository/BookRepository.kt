package com.jaemin.hermes.repository

import com.jaemin.hermes.response.BookResponse
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun searchBooks(bookName : String) : Single<List<BookResponse>>

}