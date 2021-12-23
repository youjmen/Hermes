package com.jaemin.hermes.datasource

import okhttp3.Interceptor
import okhttp3.Response

class KakaoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "KakaoAK 89db27e8d48ff472017b39eea14356c8")
            .build()

        return chain.proceed(request)
    }
}