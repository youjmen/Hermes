package com.jaemin.hermes.di

import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.datasource.remote.interceptor.CorrectionJsonInterceptor
import com.jaemin.hermes.datasource.remote.interceptor.HtmlEntityInterceptor
import com.jaemin.hermes.datasource.remote.interceptor.KakaoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single(named("book")) { provideBookRetrofit(get(named("bookClient"))) }
    single(named("location")) { provideLocationRetrofit(get(named("location"))) }
    single { CorrectionJsonInterceptor()  }
    single { HtmlEntityInterceptor() }
    single { KakaoInterceptor() }
    single(named("bookClient")) { provideBookOkHttpClient(get(),get(),get()) }
    single(named("general")) { provideOkHttpClient(get()) }
    single(named("location")) { provideLocationOkHttpClient(get(),get()) }
    single { provideLoggingInterceptor() }

}
fun provideBookRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://www.aladin.co.kr/ttb/api/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()
}
fun provideLocationRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://dapi.kakao.com/")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build()
}
fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
fun provideBookOkHttpClient(htmlEntityInterceptor: HtmlEntityInterceptor, correctionInterceptor : CorrectionJsonInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(correctionInterceptor)
        .addInterceptor(htmlEntityInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}
fun provideLocationOkHttpClient(interceptor : KakaoInterceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}
fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }}
