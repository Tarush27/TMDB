package com.example.tmdb.paging

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.MoviesModel
import com.example.tmdb.networking.MoviesService
import com.example.tmdb.room.MovieDb
import com.example.tmdb.room.MoviesAndTv
import com.example.tmdb.utils.NetworkState
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.SharedPrefsUtils
import kotlinx.coroutines.delay

private const val LOAD_DELAY_MILLIS = 3_000L
private val CURRENT_PAGE = 1

class ScreensPagingSource(
    private val service: MoviesService,
    private val screenTypes: ScreenTypes,
    private val movieDb: MovieDb,
    private val applicationContext: Context,
) : PagingSource<Int, MoviesModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel> {
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
                Log.d("screenspagingsourceon", "data: $data")
                Log.d("screenspagingsourceon", "movies: $movies")
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                if (isOfflineEnabled) {
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
                    applicationContext,
                    "You are offline! Error loading movies, Please enable wifi.",
                    Toast.LENGTH_LONG
                ).show()
                val e = Exception()
                return LoadResult.Error(e)
            }
            val currentPage = params.key ?: CURRENT_PAGE

            Log.d("screenspagingsourceoff", "loadcurrentpage: $currentPage")
            val movieTypesFromDb = when (screenTypes) {

                ScreenTypes.POPULAR -> movieDb.roomDao()
                    .getMoviesList(type = "Popular")

                ScreenTypes.TOP_RATED -> movieDb.roomDao()
                    .getMoviesList(type = "Top rated")

                ScreenTypes.UPCOMING -> movieDb.roomDao()
                    .getMoviesList(type = "Upcoming")
            }
            Log.d("screenpagmovdb", "movieTypesFromDb: $movieTypesFromDb")
            val movies = insertMoviesAndTvToPopularMovies(movieTypesFromDb)
            Log.d("screenpagingsourcetypes", "moviesTypes: $movies")
            if (currentPage != CURRENT_PAGE) delay(LOAD_DELAY_MILLIS)
            Log.d("screenspagingsourceoff", "data: $movieTypesFromDb")
            Log.d("screenspagingsourceoff", "movies: $movies")
//            val nextKey = if (movieTypesFromDb.isEmpty()) {
//                null
//            } else {
//                currentPage + 1
//            }
            return LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = null
            )

        }


    }


    override fun getRefreshKey(state: PagingState<Int, MoviesModel>): Int? {

        return null
    }

    private fun insertPopularMoviesDataToMoviesAndTv(popularMovies: List<MoviesModel>): List<MoviesAndTv> {


        val popularMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!,
                posterPath = it.posterPath,
                movieType = "Popular",
                id = it.popularMovieId
            )
        }

        return popularMovies
    }

    private fun insertTopRatedMoviesDataToMoviesAndTv(popularMovies: List<MoviesModel>): List<MoviesAndTv> {


        val topRatedMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!,
                posterPath = it.posterPath,
                movieType = "Top rated",
                id = it.popularMovieId
            )
        }

        return topRatedMovies
    }

    private fun insertUpcomingMoviesDataToMoviesAndTv(popularMovies: List<MoviesModel>): List<MoviesAndTv> {


        val upcomingMovies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!,
                posterPath = it.posterPath,
                movieType = "Upcoming",
                id = it.popularMovieId
            )
        }

        return upcomingMovies
    }

    private fun insertMoviesAndTvToPopularMovies(moviesAndTv: List<MoviesAndTv>): List<MoviesModel> {

        val popularMovies = moviesAndTv.map {
            MoviesModel(
                popularMovieTitle = it.title!!, posterPath = it.posterPath, popularMovieId = it.id
            )
        }

        Log.d("repo", "insertMoviesAndTvToPopularMovies: $popularMovies")

        return popularMovies
    }
}