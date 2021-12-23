package com.jaemin.hermes.di

import com.jaemin.hermes.datasource.LocationDataSource
import com.jaemin.hermes.datasource.LocationDataSourceImpl
import com.jaemin.hermes.main.viewmodel.LocationRegisterViewModel
import com.jaemin.hermes.remote.LocationService
import com.jaemin.hermes.repository.LocationRepository
import com.jaemin.hermes.repository.LocationRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val locationModule = module {
    fun provideLocationService(retrofit: Retrofit): LocationService =
        retrofit.create(LocationService::class.java)
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
    factory<LocationDataSource> { LocationDataSourceImpl(get()) }
    viewModel { LocationRegisterViewModel(get()) }
    single { provideLocationService(get(named("location"))) }



}