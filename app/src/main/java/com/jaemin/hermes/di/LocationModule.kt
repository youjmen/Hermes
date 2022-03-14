package com.jaemin.hermes.di

import com.jaemin.hermes.data.datasource.LocationDataSource
import com.jaemin.hermes.data.datasource.LocationDataSourceImpl
import com.jaemin.hermes.features.main.viewmodel.LocationRegisterViewModel
import com.jaemin.hermes.data.datasource.remote.LocationService
import com.jaemin.hermes.data.repository.LocationRepository
import com.jaemin.hermes.data.repository.LocationRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val locationModule = module {
    fun provideLocationService(retrofit: Retrofit): LocationService =
        retrofit.create(LocationService::class.java)
    single { provideLocationService(get(named("location"))) }
    factory<LocationDataSource> { LocationDataSourceImpl(get(),get()) }
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
    viewModel { LocationRegisterViewModel(get()) }



}