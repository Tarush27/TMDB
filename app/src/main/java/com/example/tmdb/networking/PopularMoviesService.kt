package com.example.tmdb.networking

import com.example.tmdb.model.PopularMoviesResponse
import com.example.tmdb.model.TrendingTVShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PopularMoviesService {
    @GET("movie/popular?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getPopularMoviesResponse(
    ): Call<PopularMoviesResponse>

    @GET("movie/top_rated?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getTopRatedMoviesResponse(): Call<PopularMoviesResponse>

    @GET("movie/upcoming?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getUpcomingMoviesResponse(): Call<PopularMoviesResponse>

    @GET("trending/movie/{time_window}?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getTrendingMoviesResponse(@Path("time_window") time_window: String): Call<PopularMoviesResponse>

    @GET("trending/tv/{time_window}?api_key=1f590be8f881ebce14b4088e08124d84")
    fun getTrendingTVShowsResponse(@Path("time_window") time_window: String): Call<TrendingTVShowsResponse>


}