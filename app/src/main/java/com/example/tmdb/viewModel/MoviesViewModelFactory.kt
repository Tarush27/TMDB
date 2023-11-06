package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.repository.MoviesRepository

class MoviesViewModelFactory(
    private val moviesRepository: MoviesRepository,
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(moviesRepository) as T
    }
}