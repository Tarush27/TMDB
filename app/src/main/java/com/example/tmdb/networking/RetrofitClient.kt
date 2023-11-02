package com.example.tmdb.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .client(OkHttpLoginInterceptor.okHttpClient.apply {
            println("read time out: $readTimeoutMillis")
            println("connection time out: $connectTimeoutMillis")
        })
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PopularMoviesService::class.java)
}