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
import retrofit2.HttpException

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
        if (!NetworkState.isInternetAvailable(applicationContext)) {
            Toast.makeText(applicationContext, "No records found", Toast.LENGTH_SHORT).show()
        }
        else{
//            val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//            if (!isOfflineEnabled) {
////                Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                Log.d("screenspagingsource", "You are offline ")
//            }
        }
        return try {
            val currentPage = params.key ?: CURRENT_PAGE
            if (currentPage != CURRENT_PAGE) delay(LOAD_DELAY_MILLIS)
            Log.d("screenspagingsource", "loadcurrentpage: $currentPage")
            val response = when (screenTypes) {

                ScreenTypes.POPULAR -> service.getPopularMoviesResponsePerPage(currentPage)
                ScreenTypes.TOP_RATED -> service.getTopRatedMoviesResponsePerPage(
                    currentPage
                )

                ScreenTypes.UPCOMING -> service.getUpcomingMoviesResponsePerPage(currentPage)
            }
            Log.d("screenspagingsource", "load response: $response")
            val data = response.body()!!.popularMovies
            val movies = insertPopularMoviesDataToMoviesAndTv(data)
            Log.d("screenspagingsource", "data: $data")
            Log.d("screenspagingsource", "movies: $movies")
//            val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//            if (isOfflineEnabled) {
////                Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                movieDb.roomDao().insertMovies(movies)
//            }
//            else{
//                Log.d("screenspagingsource", "offline not enabled ")
//            }
            movieDb.roomDao().insertMovies(movies)
            val nextKey = if (data.isEmpty()) {
                null
            } else {
                currentPage + 1
            }
            LoadResult.Page(
                data = data, prevKey = if (currentPage == 1) null else -1, nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
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

        val movies = popularMovies.map {
            MoviesAndTv(
                title = it.popularMovieTitle!!,
                posterPath = it.posterPath
            )
        }

        return movies
    }
}