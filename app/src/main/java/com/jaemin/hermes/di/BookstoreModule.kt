package com.jaemin.hermes.di

import com.jaemin.hermes.book.viewmodel.CheckStockViewModel
import com.jaemin.hermes.bookstore.viewmodel.BookstoreSearchViewModel
import com.jaemin.hermes.datasource.remote.KyoboBooksScraper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookstoreModule = module {
    viewModel { CheckStockViewModel(get(), get()) }
    viewModel { BookstoreSearchViewModel(get()) }
    factory { KyoboBooksScraper() }

}