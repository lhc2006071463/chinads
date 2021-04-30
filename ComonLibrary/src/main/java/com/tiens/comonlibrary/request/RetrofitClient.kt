package com.tiens.comonlibrary.request

import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
        private lateinit var retrofit: Retrofit
        const val BASE_URL: String = "http://www.baidu.com"
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    init {
        retrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        Log.d("Tag","getOkHttpClient----")
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    fun create(): ApiService = retrofit.create(ApiService::class.java)

}
