package com.jaemin.hermes.di

import com.jaemin.hermes.book.viewmodel.CheckStockViewModel
import com.jaemin.hermes.datasource.remote.KyoboBooksScraper
import org.koin.dsl.module

val bookstoreModule = module {
    factory { CheckStockViewModel(get(), get()) }
    factory { KyoboBooksScraper() }

}