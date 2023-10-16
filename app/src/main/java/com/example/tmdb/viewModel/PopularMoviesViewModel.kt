package com.example.tmdb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.tmdb.Utils.TimeWindow
import com.example.tmdb.Utils.stringAbc
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.paging.ScreensPagingSource
import com.example.tmdb.repository.PopularMoviesRepository

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {

//    fun getPopularMovies(page: Int) = popularMoviesRepository.getPopularMovies(page)
//    val getPopularMoviesResponse = popularMoviesRepository.popularMoviesResponse
//
//    fun getPopularMovieList(): LiveData<PagingData<PopularMoviesModel>> {
//        return popularMoviesRepository.getPopularMovies().cachedIn(viewModelScope)
//    }
//
//    val popularMoviesList = popularMoviesRepository.getPopularMovies().cachedIn(viewModelScope)
//
//    fun getPopularMoviesList(): LiveData<PagingData<PopularMoviesModel>> {
//        return popularMoviesRepository.getAllPopularMovies().cachedIn(viewModelScope)
//    }
//
//        val popularMoviesList = popularMoviesRepository.getPopularMovies().cachedIn(viewModelScope)
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

    val popularMoviesList: LiveData<PagingData<PopularMoviesModel>> = Pager(PagingConfig(pageSize = 1, enablePlaceholders = false), initialKey = 1) {
        ScreensPagingSource(popularMoviesRepository)
    }.liveData

    val getMoviesDetails = popularMoviesRepository.movieDetailsResponse

    fun getTrendingTVShowsDetails(id: Int) {
        popularMoviesRepository.getTrendingTVShowsDetails(id)
    }

    val getTrendingTvShowsDetails = popularMoviesRepository.trendingTVShowsDetailsResponse

    fun getNowPlayingMoviesDetails() = popularMoviesRepository.getNowPlayingMoviesDetails()

    val getNowPlayingMoviesDetails = popularMoviesRepository.nowPlayingMoviesResponse
}