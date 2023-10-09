package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import com.example.tmdb.repository.PopularMoviesRepository

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {
    suspend fun getPopularMovies() = popularMoviesRepository.getPopularMovies()
    val getPopularMoviesResponse = popularMoviesRepository.popularMoviesResponse
}