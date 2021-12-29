package com.jaemin.hermes.di

import com.jaemin.hermes.book.viewmodel.BookViewModel
import com.jaemin.hermes.datasource.remote.BookDataSource
import com.jaemin.hermes.datasource.remote.BookDataSourceImpl
import com.jaemin.hermes.remote.BookService
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.repository.BookRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val bookModule = module {
    fun provideBookService(retrofit: Retrofit): BookService =
        retrofit.create(BookService::class.java)
    single { provideBookService(get(named("book"))) }
    factory<BookDataSource> { BookDataSourceImpl(get()) }
    factory<BookRepository> { BookRepositoryImpl(get()) }
    viewModel { BookViewModel(get()) }



}