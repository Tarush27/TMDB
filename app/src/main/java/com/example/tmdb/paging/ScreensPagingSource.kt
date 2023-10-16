package com.example.tmdb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.repository.PopularMoviesRepository
import retrofit2.HttpException

class ScreensPagingSource(private val popularMoviesRepository: PopularMoviesRepository) :
    PagingSource<Int, PopularMoviesModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMoviesModel> {
        return try {
            val currentPage = params.key ?: 1
            val response = popularMoviesRepository.getPopularMovies(currentPage)
            val data = response.body()!!.popularMovies
            val responseData = mutableListOf<PopularMoviesModel>()
            responseData.addAll(data)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PopularMoviesModel>): Int? {
        //
        return null
    }
}