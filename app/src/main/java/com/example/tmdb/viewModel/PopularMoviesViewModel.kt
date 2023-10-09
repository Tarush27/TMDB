package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.repository.PopularMoviesRepository
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {
    fun getPopularMovies() = popularMoviesRepository.getPopularMovies()


    val getPopularMoviesResponse = popularMoviesRepository.popularMoviesResponse
}