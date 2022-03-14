package com.jaemin.hermes.di

import com.jaemin.hermes.features.book.viewmodel.BookDetailViewModel
import com.jaemin.hermes.features.book.viewmodel.BookViewModel
import com.jaemin.hermes.data.datasource.BookDataSource
import com.jaemin.hermes.data.datasource.BookDataSourceImpl
import com.jaemin.hermes.data.datasource.remote.BookService
import com.jaemin.hermes.data.repository.BookRepository
import com.jaemin.hermes.data.repository.BookRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val bookModule = module {
    fun provideBookService(retrofit: Retrofit): BookService =
        retrofit.create(BookService::class.java)
    single { provideBookService(get(named("book"))) }
    factory<BookDataSource> { BookDataSourceImpl(get(), get()) }
    factory<BookRepository> { BookRepositoryImpl(get()) }
    viewModel { BookViewModel(get()) }
    viewModel { BookDetailViewModel(get()) }



}