package com.jaemin.hermes.repository

import com.jaemin.hermes.entity.Book
import io.reactivex.rxjava3.core.Single

interface BookRepository {
    fun searchBooks(bookName : String) : Single<List<Book>>

    fun getBookInformation(isbn : String) : Single<Book>

}