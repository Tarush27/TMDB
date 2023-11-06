package com.example.tmdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tmdb.model.MoviesModel
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.utils.TimeWindow
import com.example.tmdb.utils.convertToDayWeekString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepository: MoviesRepository) :
    ViewModel() {


    fun getPopularMovies() = viewModelScope.launch {
        moviesRepository.getPopularMovies()
    }

    val getPopularMoviesResponse = moviesRepository.popularMoviesResponse

    fun getTopRatedMovies() = viewModelScope.launch {
        moviesRepository.getTopRatedMovies()
    }

    val getTopRatedMoviesResponse = moviesRepository.topRatedMoviesResponse

    fun getUpComingMovies() = viewModelScope.launch {
        moviesRepository.getUpcomingMovies()
    }

    val getUpComingMovies = moviesRepository.upComingMoviesResponse


    fun getTrendingMovies(isDayOrWeek: Boolean) {
        viewModelScope.launch {
            moviesRepository.getTrendingMovies(
                (if (isDayOrWeek) TimeWindow.WEEK
                else TimeWindow.DAY).convertToDayWeekString()
            )
        }
    }

    val getTrendingMovies = moviesRepository.trendingMoviesResponse

    fun getTrendingTVShows(isDayOrWeek: Boolean) {
        viewModelScope.launch {
            moviesRepository.getTrendingTVShows(
                (if (isDayOrWeek) TimeWindow.WEEK
                else TimeWindow.DAY).convertToDayWeekString()
            )
        }
    }

    val getTrendingTVShows = moviesRepository.trendingTVShowsResponse

    fun getMoviesDetails(id: Long, type: String?) {
        viewModelScope.launch {
            moviesRepository.getMoviesDetails(id, type)
        }

    }


    fun getPopularMoviesPageWise(): Flow<PagingData<MoviesModel>> {
        return moviesRepository.getPopularMoviesPerPage()

    }

    fun getTopRatedMoviesPageWise(): Flow<PagingData<MoviesModel>> {

        return moviesRepository.getTopRatedMoviesPerPage()

    }

    fun getUpcomingMoviesPageWise(): Flow<PagingData<MoviesModel>> {

        return moviesRepository.getUpcomingMoviesPerPage()

    }

    val getMoviesDetails = moviesRepository.movieDetailsResponse

    fun getTrendingTVShowsDetails(id: Long, trendingTvShows: String?) {
        viewModelScope.launch {

            moviesRepository.getTrendingTVShowsDetails(id, trendingTvShows)
        }
    }

    val getTrendingTvShowsDetails = moviesRepository.trendingTVShowsDetailsResponse

    fun getNowPlayingMoviesDetails() = viewModelScope.launch {
        moviesRepository.getNowPlayingMoviesDetails()
    }

    val getNowPlayingMoviesDetails = moviesRepository.nowPlayingMoviesResponse
}