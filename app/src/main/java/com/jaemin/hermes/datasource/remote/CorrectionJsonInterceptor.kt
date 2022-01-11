package com.jaemin.hermes.datasource.remote

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class CorrectionJsonInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 200 && response.body != null){

            if (response.peekBody(MAX_BYTE).string().last()==';'){
                val jsonBody =response.peekBody(MAX_BYTE).string().dropLast(1)
                val contentType : MediaType = response.body!!.contentType()!!
                val convertedBody = jsonBody.toResponseBody(contentType)
                return response.newBuilder().body(convertedBody).build()
            }
        }
        return chain.proceed(request)
    }
    companion object{
        private const val MAX_BYTE = 100000000L
    }
}