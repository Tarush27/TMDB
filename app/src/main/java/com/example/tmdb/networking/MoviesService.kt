package com.example.tmdb.networking

import com.example.tmdb.model.DetailsResponse
import com.example.tmdb.model.NowPlayingMoviesResponse
import com.example.tmdb.model.MoviesResponse
import com.example.tmdb.model.TrendingTVShowsDetailsResponse
import com.example.tmdb.model.TrendingTVShowsResponse
import com.example.tmdb.utils.MoviesUtils.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesResponsePerPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMoviesResponse(@Query("api_key") apiKey: String = API_KEY): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesResponse(@Query("api_key") apiKey: String = API_KEY): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesResponse(@Query("api_key") apiKey: String = API_KEY): Response<NowPlayingMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesResponse(@Query("api_key") apiKey: String = API_KEY): Response<MoviesResponse>

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMoviesResponse(
        @Path("time_window") time_window: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MoviesResponse>

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingTVShowsResponse(
        @Path("time_window") time_window: String,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<TrendingTVShowsResponse>

    @GET("movie/{movie_id}")
    suspend fun getMoviesDetails(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<DetailsResponse>

    @GET("tv/{series_id}")
    suspend fun getTrendingTvShowsDetails(
        @Path("series_id") series_id: Long,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<TrendingTVShowsDetailsResponse>


}