package com.example.tmdb.networking

import com.example.tmdb.model.DetailsResponse
import com.example.tmdb.model.NowPlayingMoviesResponse
import com.example.tmdb.model.PopularMoviesResponse
import com.example.tmdb.model.TrendingTVShowsDetailsResponse
import com.example.tmdb.model.TrendingTVShowsResponse
import com.example.tmdb.utils.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PopularMoviesService {
    @GET("movie/popular")
    suspend fun getPopularMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<PopularMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<PopularMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<PopularMoviesResponse>

    @GET("movie/popular?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getPopularMoviesResponse(): Response<PopularMoviesResponse>

    @GET("movie/top_rated?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getTopRatedMoviesResponse(): Response<PopularMoviesResponse>

    @GET("movie/now_playing?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getNowPlayingMoviesResponse(): Response<NowPlayingMoviesResponse>

    @GET("movie/upcoming?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getUpcomingMoviesResponse(): Response<PopularMoviesResponse>

    @GET("trending/movie/{time_window}?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getTrendingMoviesResponse(@Path("time_window") time_window: String): Response<PopularMoviesResponse>

    @GET("trending/tv/{time_window}?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getTrendingTVShowsResponse(@Path("time_window") time_window: String): Response<TrendingTVShowsResponse>

    @GET("movie/{movie_id}?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getMoviesDetails(@Path("movie_id") movie_id: Long): Response<DetailsResponse>

    @GET("tv/{series_id}?api_key=1f590be8f881ebce14b4088e08124d84")
    suspend fun getTrendingTvShowsDetails(@Path("series_id") series_id: Long): Response<TrendingTVShowsDetailsResponse>


}