package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.tmdb.paging.ScreensPagingSource
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.TimeWindow
import com.example.tmdb.utils.stringAbc
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {


    fun getPopularMovies() = viewModelScope.launch {
        popularMoviesRepository.getPopularMovies()
    }

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

    fun getMoviesDetails(id: Int) {
        popularMoviesRepository.getMoviesDetails(id)
    }

    val popularMoviesList =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(popularMoviesRepository, ScreenTypes.POPULAR)
        }.flow

    val upComingMoviesList =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(popularMoviesRepository, ScreenTypes.UPCOMING)
        }.flow

    val topRatedMoviesList =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(popularMoviesRepository, ScreenTypes.TOP_RATED)
        }.flow

    val getMoviesDetails = popularMoviesRepository.movieDetailsResponse

    fun getTrendingTVShowsDetails(id: Int) {
        popularMoviesRepository.getTrendingTVShowsDetails(id)
    }

    val getTrendingTvShowsDetails = popularMoviesRepository.trendingTVShowsDetailsResponse

    fun getNowPlayingMoviesDetails() = popularMoviesRepository.getNowPlayingMoviesDetails()

    val getNowPlayingMoviesDetails = popularMoviesRepository.nowPlayingMoviesResponse
}