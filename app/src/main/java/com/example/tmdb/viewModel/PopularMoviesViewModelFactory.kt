package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.repository.PopularMoviesRepository

class PopularMoviesViewModelFactory(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularMoviesViewModel(popularMoviesRepository) as T
    }
}