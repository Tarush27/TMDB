package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import com.example.tmdb.Utils.TimeWindow
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.Utils.stringAbc

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {

    fun getPopularMovies() = popularMoviesRepository.getPopularMovies()
    val getPopularMoviesResponse = popularMoviesRepository.popularMoviesResponse

    fun getTopRatedMovies() = popularMoviesRepository.getTopRatedMovies()
    val getTopRatedMoviesResponse = popularMoviesRepository.topRatedMoviesResponse

    fun getUpComingMovies() = popularMoviesRepository.getUpcomingMovies()
    val getUpComingMovies = popularMoviesRepository.upComingMoviesResponse


    fun getTrendingMovies(isDayOrWeek: Boolean) {
        popularMoviesRepository.getTrendingMovies(
            (if (isDayOrWeek)
                TimeWindow.WEEK
            else TimeWindow.DAY)
                .stringAbc()
        )
    }

    val getTrendingMovies = popularMoviesRepository.trendingMoviesResponse

    fun getTrendingTVShows(isDayOrWeek: Boolean) {
        popularMoviesRepository.getTrendingTVShows(
            (if (isDayOrWeek)
                TimeWindow.WEEK
            else TimeWindow.DAY)
                .stringAbc()
        )
    }

    val getTrendingTVShows = popularMoviesRepository.trendingTVShowsResponse
}