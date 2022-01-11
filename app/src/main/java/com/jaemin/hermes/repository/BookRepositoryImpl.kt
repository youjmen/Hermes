package com.jaemin.hermes.repository

import com.jaemin.hermes.datasource.BookDataSource
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.core.Single

class BookRepositoryImpl(private val bookDataSource: BookDataSource) : BookRepository {
    override fun searchBooks(bookName: String): Single<List<Book>> =
        bookDataSource.searchBooks(bookName).map {
            it.item.map { response -> response.toEntity() }
        }


    override fun getBookInformation(isbn: String): Single<Book> =
        bookDataSource.getBookInformation(isbn).map {
            it.item.first().toEntity()
        }

    override fun getBestSellers(): Single<List<Book>> =
        bookDataSource.getBestSellers().map {
            it.item.map { response -> response.toEntity() }
        }

    override fun getNewSpecialBooks(): Single<List<Book>> =
        bookDataSource.getNewSpecialBooks().map {
            it.item.map { response -> response.toEntity() }
        }


    override fun getNewBooks(): Single<List<Book>> =
        bookDataSource.getNewBooks().map {
            it.item.map { response -> response.toEntity() }
        }


}