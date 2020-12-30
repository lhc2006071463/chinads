package com.tiens.comonlibrary.request

import com.tiens.comonlibrary.util.ALog
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.net.URLDecoder

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.currentTimeMillis()
        var requestLog = String.format(
            "Sending request %s on %s%n%s",
            request.url(), chain.connection(), request.headers()
        )
        if (request.method().compareTo("post", ignoreCase = true) == 0) {
            if (request.body() is FormBody) {
                val requestBody = bodyToString(request)
                requestLog = """
                    
                    $requestLog
                    $requestBody
                    """.trimIndent()
            }
        }
        ALog.d(requestLog)
        val bodyString: String
        val response = chain.proceed(request)
        val t2 = System.currentTimeMillis()
        bodyString = if (response.body() == null) "" else response.body()!!.string()
        ALog.d(request.url().toString() + "请求耗时:" + (t2 - t1))
        ALog.json(bodyString)
        return response.newBuilder()
            .body(ResponseBody.create(response.body()!!.contentType(), bodyString))
            .build()
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            URLDecoder.decode(buffer.readUtf8(), "utf-8")
        } catch (e: IOException) {
            "did not work"
        }
    }
}