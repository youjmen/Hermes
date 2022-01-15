package com.jaemin.hermes.datasource.remote.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class HtmlEntityInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 200 && response.body != null) {
            val jsonBody = response.peekBody(MAX_BYTE).string().convertHtmlEntity()
            val contentType: MediaType = response.body!!.contentType()!!
            val convertedBody = jsonBody.toResponseBody(contentType)
            return response.newBuilder().body(convertedBody).build()

        }
        return chain.proceed(request)
    }
    private fun String.convertHtmlEntity(): String {
        return replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&amp;", "&")
    }

    companion object {
        private const val MAX_BYTE = 100000000L
    }
}