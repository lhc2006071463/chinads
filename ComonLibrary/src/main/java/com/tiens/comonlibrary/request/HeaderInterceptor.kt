package com.tiens.comonlibrary.request

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val build = builder
            .addHeader("token", "")
            .build()
        return chain.proceed(build)
    }
}