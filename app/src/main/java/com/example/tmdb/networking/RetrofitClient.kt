package com.example.tmdb.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .client(OkHttpLoginInterceptor.okHttpClient)
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(PopularMoviesService::class.java)
}