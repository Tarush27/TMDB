package com.example.tmdb.networking

import com.example.tmdb.model.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMoviesService {
    @GET("popular?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getPopularMoviesResponse(
    ): Call<PopularMoviesResponse>

    @GET("top_rated?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getTopRatedMoviesResponse(): Call<PopularMoviesResponse>
}