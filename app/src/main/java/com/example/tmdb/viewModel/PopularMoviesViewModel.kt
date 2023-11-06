package com.example.tmdb.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.TimeWindow
import com.example.tmdb.utils.stringAbc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PopularMoviesViewModel(private val popularMoviesRepository: PopularMoviesRepository) :
    ViewModel() {


    fun getPopularMovies() = viewModelScope.launch {
        popularMoviesRepository.getPopularMovies()
    }

    val getPopularMoviesResponse = popularMoviesRepository.popularMoviesResponse

    fun getTopRatedMovies() = viewModelScope.launch {
        popularMoviesRepository.getTopRatedMovies()
    }

    val getTopRatedMoviesResponse = popularMoviesRepository.topRatedMoviesResponse

    fun getUpComingMovies() = viewModelScope.launch {
        popularMoviesRepository.getUpcomingMovies()
    }

    val getUpComingMovies = popularMoviesRepository.upComingMoviesResponse


    fun getTrendingMovies(isDayOrWeek: Boolean) {
        viewModelScope.launch {
            popularMoviesRepository.getTrendingMovies(
                (if (isDayOrWeek) TimeWindow.WEEK
                else TimeWindow.DAY).stringAbc()
            )
        }
    }

    val getTrendingMovies = popularMoviesRepository.trendingMoviesResponse

    fun getTrendingTVShows(isDayOrWeek: Boolean) {
        viewModelScope.launch {
            popularMoviesRepository.getTrendingTVShows(
                (if (isDayOrWeek) TimeWindow.WEEK
                else TimeWindow.DAY).stringAbc()
            )
        }
    }

    val getTrendingTVShows = popularMoviesRepository.trendingTVShowsResponse

    fun getMoviesDetails(id: Long, type: String?) {
        viewModelScope.launch {
            popularMoviesRepository.getMoviesDetails(id, type)
        }

    }


    fun getPopularMoviesPageWise(): Flow<PagingData<PopularMoviesModel>> {
        //check internet

//        val popularMoviesPageWiseResponse = popularMoviesRepository.getPopularMoviesPerPage()
//        popularMoviesPageWisePagingResponse.value = popularMoviesPageWiseResponse.value
//        //no internet
////        return popularMoviesRepository.getPopularMovies()
//        return popularMoviesPageWiseResponse


        return popularMoviesRepository.getPopularMoviesPerPage()
        //no internet
//        return popularMoviesRepository.getPopularMovies()


    }

    fun getTopRatedMoviesPageWise(): Flow<PagingData<PopularMoviesModel>> {

        return popularMoviesRepository.getTopRatedMoviesPerPage()

    }

    fun getUpcomingMoviesPageWise(): Flow<PagingData<PopularMoviesModel>> {

        return popularMoviesRepository.getUpcomingMoviesPerPage()

    }

    val getMoviesDetails = popularMoviesRepository.movieDetailsResponse

    fun getTrendingTVShowsDetails(id: Long, trendingTvShows: String?) {
        viewModelScope.launch {

            popularMoviesRepository.getTrendingTVShowsDetails(id, trendingTvShows)
        }
    }

    val getTrendingTvShowsDetails = popularMoviesRepository.trendingTVShowsDetailsResponse

    fun getNowPlayingMoviesDetails() = viewModelScope.launch {
        popularMoviesRepository.getNowPlayingMoviesDetails()
    }

    val getNowPlayingMoviesDetails = popularMoviesRepository.nowPlayingMoviesResponse
}