package com.example.tmdb.networking

import com.example.tmdb.utils.MoviesUtils.Companion.API_ACCESS_TOKEN
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpLoginInterceptor {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
    val okHttpClient = OkHttpClient.Builder().readTimeout(100, TimeUnit.SECONDS)
        .connectTimeout(100, TimeUnit.SECONDS).addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val newRequest: Request =
                originalRequest.newBuilder().addHeader("API_AUTH_TOKEN", API_ACCESS_TOKEN).build()
            return@addInterceptor chain.proceed(newRequest)
        }.addInterceptor(httpLoggingInterceptor).build()
}