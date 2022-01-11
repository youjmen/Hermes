package com.jaemin.hermes.di

import com.jaemin.hermes.book.viewmodel.CheckStockViewModel
import org.koin.dsl.module

val bookstoreModule = module {
    factory { CheckStockViewModel(get()) }
}