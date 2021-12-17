package com.jaemin.hermes.di

import com.jaemin.hermes.book.viewmodel.BookViewModel
import com.jaemin.hermes.remote.BookService
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.repository.BookRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val bookModule = module {
    fun provideBookService(retrofit: Retrofit): BookService =
        retrofit.create(BookService::class.java)
    factory<BookRepository> { BookRepositoryImpl(provideBookService(get())) }
    factory { BookViewModel(get()) }
    single { provideBookService(get()) }



}