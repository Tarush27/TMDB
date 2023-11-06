package com.example.tmdb.paging

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.room.MovieDb
import com.example.tmdb.room.MoviesAndTv
import com.example.tmdb.utils.NetworkState
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.SharedPrefsUtils
import kotlinx.coroutines.delay

private const val LOAD_DELAY_MILLIS = 3_000L
private val CURRENT_PAGE = 1

class ScreensPagingSource(
    /*private val popularMoviesRepository: PopularMoviesRepository*/
    private val service: PopularMoviesService,
    private val screenTypes: ScreenTypes,
    private val movieDb: MovieDb,
    private val applicationContext: Context,
) : PagingSource<Int, PopularMoviesModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMoviesModel> {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val currentPage = params.key ?: CURRENT_PAGE
                if (currentPage != CURRENT_PAGE) delay(LOAD_DELAY_MILLIS)
                Log.d("screenspagingsourceon", "loadcurrentpage: $currentPage")
                val response = when (screenTypes) {

                    ScreenTypes.POPULAR -> service.getPopularMoviesResponsePerPage(currentPage)
                    ScreenTypes.TOP_RATED -> service.getTopRatedMoviesResponsePerPage(
                        currentPage
                    )

                    ScreenTypes.UPCOMING -> service.getUpcomingMoviesResponsePerPage(currentPage)
                }
                Log.d("screenspagingsourceon", "load response: $response")
                val data = response.body()!!.popularMovies
                val movies = when (screenTypes) {

                    ScreenTypes.POPULAR -> insertPopularMoviesDataToMoviesAndTv(data)
                    ScreenTypes.TOP_RATED -> insertTopRatedMoviesDataToMoviesAndTv(data)

                    ScreenTypes.UPCOMING -> insertUpcomingMoviesDataToMoviesAndTv(data)
                }
//                val movies = insertPopularMoviesDataToMoviesAndTv(data)
                Log.d("screenspagingsourceon", "data: $data")
                Log.d("screenspagingsourceon", "movies: $movies")
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                if (isOfflineEnabled) {
//                Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
                    movieDb.roomDao().insertMovies(movies)
                } else {
                    Log.d("screenspagingsource", "offline not enabled ")
                }
                val nextKey = if (data.isEmpty()) {
                    null
                } else {
                    currentPage + 1
                }
                return LoadResult.Page(
                    data = data, prevKey = if (currentPage == 1) null else -1, nextKey = nextKey
                )
            } catch (e: Exception) {
                return LoadResult.Error(e)
            }

        } else {
            val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
            if (!isOfflineEnabled) {
                Toast.makeText(
                    applicationContext, "You are offline! Error loading movies, Please enable wifi.", Toast.LENGTH_LONG
                ).show()
                val e = Exception()
                return LoadResult.Error(e)
            }
            val currentPage = params.key ?: CURRENT_PAGE

            Log.d("screenspagingsourceoff", "loadcurrentpage: $currentPage")
            val movieTypesFromDb = when (screenTypes) {

                ScreenTypes.POPULAR -> movieDb.roomDao()
                    .getPagedList(type = "Popular")

                ScreenTypes.TOP_RATED -> movieDb.roomDao()
                    .getPagedList(type = "Top rated")

                ScreenTypes.UPCOMING -> movieDb.roomDao()
                    .getPagedList(type = "Upcoming")
            }
            Log.d("screenpagmovdb", "movieTypesFromDb: $movieTypesFromDb")
            val movies = insertMoviesAndTvToPopularMovies(movieTypesFromDb)
            Log.d("screenpagingsourcetypes", "moviesTypes: $movies")
            if (currentPage != CURRENT_PAGE) delay(LOAD_DELAY_MILLIS)
            Log.d("screenspagingsourceoff", "data: $movieTypesFromDb")
            Log.d("screenspagingsourceoff", "movies: $movies")
            val nextKey = if (movieTypesFromDb.isEmpty()) {
                null
            } else {
                currentPage + 1
            }
            return LoadResult.Page(
                data = movies, prevKey = if (currentPage == 1) null else -1, nextKey = nextKey
            )

        }


    }


    override fun getRefreshKey(state: PagingState<Int, PopularMoviesModel>): Int? {

//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }

        return null
    }

    private fun insertPopularMoviesDataToMoviesAndTv(popularMovies: List<PopularMoviesModel>): List<MoviesAndTv> {


        val popularMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!, posterPath = it.posterPath, movieType = "Popular"
            )
        }

        return popularMovies
    }

    private fun insertTopRatedMoviesDataToMoviesAndTv(popularMovies: List<PopularMoviesModel>): List<MoviesAndTv> {


        val topRatedMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!, posterPath = it.posterPath, movieType = "Top rated"
            )
        }

        return topRatedMovies
    }

    private fun insertUpcomingMoviesDataToMoviesAndTv(popularMovies: List<PopularMoviesModel>): List<MoviesAndTv> {


        val upcomingMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!, posterPath = it.posterPath, movieType = "Upcoming"
            )
        }

        return upcomingMovies
    }

    private fun insertMoviesAndTvToPopularMovies(moviesAndTv: List<MoviesAndTv>): List<PopularMoviesModel> {

        val popularMovies = moviesAndTv.map {
            PopularMoviesModel(
                popularMovieTitle = it.title!!, posterPath = it.posterPath, popularMovieId = it.id
            )
        }

        Log.d("repo", "insertMoviesAndTvToPopularMovies: $popularMovies")

        return popularMovies
    }
}