package com.jaemin.hermes.di

import com.jaemin.hermes.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get(),get()) }
}