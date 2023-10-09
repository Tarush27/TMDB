package com.example.tmdb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdb.model.PopularMoviesResponse
import com.example.tmdb.networking.PopularMoviesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesRepository(private val popularMoviesService: PopularMoviesService) {
    private val _popularMoviesResponse = MutableLiveData<PopularMoviesResponse>()
    val popularMoviesResponse: LiveData<PopularMoviesResponse> = _popularMoviesResponse
    suspend fun getPopularMovies() {
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
}