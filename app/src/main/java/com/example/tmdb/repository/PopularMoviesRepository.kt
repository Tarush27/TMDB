package com.example.tmdb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdb.TimeWindow
import com.example.tmdb.model.PopularMoviesResponse
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.stringAbc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesRepository(private val popularMoviesService: PopularMoviesService) {
    private val _popularMoviesResponse = MutableLiveData<PopularMoviesResponse>()
    val popularMoviesResponse: LiveData<PopularMoviesResponse> = _popularMoviesResponse

    private val _topRatedMoviesResponse = MutableLiveData<PopularMoviesResponse>()
    val topRatedMoviesResponse: LiveData<PopularMoviesResponse> = _topRatedMoviesResponse

    private val _upComingMoviesResponse = MutableLiveData<PopularMoviesResponse>()
    val upComingMoviesResponse: LiveData<PopularMoviesResponse> = _upComingMoviesResponse

    private val _trendingMoviesResponse = MutableLiveData<PopularMoviesResponse>()
    val trendingMoviesResponse: LiveData<PopularMoviesResponse> = _trendingMoviesResponse
    fun getPopularMovies() {
        val response: Call<PopularMoviesResponse> = popularMoviesService.getPopularMoviesResponse()
        response.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                _popularMoviesResponse.value = response.body()
                Log.d("repo", response.toString())
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("repo", t.message.toString())
            }

        })
    }

    fun getTopRatedMovies() {
        val response: Call<PopularMoviesResponse> = popularMoviesService.getTopRatedMoviesResponse()
        response.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                _topRatedMoviesResponse.value = response.body()
                Log.d("repo", response.toString())
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("repo", t.message.toString())
            }

        })
    }

    fun getUpcomingMovies() {
        val response: Call<PopularMoviesResponse> = popularMoviesService.getUpcomingMoviesResponse()
        response.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                _upComingMoviesResponse.value = response.body()
                Log.d("repo", response.toString())
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("repo", t.message.toString())
            }

        })
    }



    fun getTrendingMovies(type: String) {

        val response: Call<PopularMoviesResponse> =
            popularMoviesService.getTrendingMoviesResponse(type)
        response.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                _trendingMoviesResponse.value = response.body()
                Log.d("trendingMovies", "trendingMovies : $response")
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.d("repo", t.message.toString())
            }

        })
    }
}