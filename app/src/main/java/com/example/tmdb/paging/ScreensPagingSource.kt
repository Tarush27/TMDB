package com.example.tmdb.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.ScreenTypes
import retrofit2.HttpException

class ScreensPagingSource(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val screenTypes: ScreenTypes
) : PagingSource<Int, PopularMoviesModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMoviesModel> {
        return try {
            val currentPage = params.key ?: 1
            Log.d("screenspagingsource", "loadcurrentpage: $currentPage")
//            val response = popularMoviesRepository.getPopularMoviesPerPage(currentPage)
            val response = when (screenTypes) {
                ScreenTypes.POPULAR -> popularMoviesRepository.getPopularMoviesPerPage(currentPage)
                ScreenTypes.TOP_RATED -> popularMoviesRepository.getTopRatedMoviesPerPage(
                    currentPage
                )

                ScreenTypes.UPCOMING -> popularMoviesRepository.getUpcomingMoviesPerPage(currentPage)
            }
            Log.d("screenspagingsource", "load response: $response")
            val data = response.body()?.popularMovies ?: emptyList()
            Log.d("screenspagingsource", "data: $data")
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
        //
//        return null

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}