package com.example.tmdb.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.repository.PopularMoviesRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException

class ScreensPagingSource(private val popularMoviesRepository: PopularMoviesRepository) :
    PagingSource<Int, PopularMoviesModel>() {

//    private val initialPage = 1
//    private val cache = mutableMapOf<Int, List<PopularMoviesModel>>()

    //    private var nextPageToLoad = 1
//    private var isCurrentlyLoading = false
    private val initialPage = 1
    private var isFirstPageLoaded = false
    private var isLoading = false
    private val mutex = Mutex()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularMoviesModel> {
        return try {
            val currentPage = params.key ?: 1
            val intialPageSize = 3 * currentPage
            Log.d("screenspagingsource", "loadcurrentpage: $currentPage")
            val response = popularMoviesRepository.getPopularMoviesPerPage(intialPageSize)
            Log.d("screenspagingsource", "load response: $response")
            LoadResult.Page(
                data = response.body()!!.popularMovies,
                prevKey = if (intialPageSize == 1) null else -1,
                nextKey = if (intialPageSize == response.body()!!.totalPages) null else intialPageSize.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpException: HttpException) {
            LoadResult.Error(httpException)
        }

//        try {
//            val currentPage = params.key ?: initialPage
//
//            // Check if data is already in cache
//            if (cache.containsKey(currentPage)) {
//                val data = cache[currentPage]
//                return LoadResult.Page(
//                    data = data ?: emptyList(),
//                    prevKey = if (currentPage == initialPage) null else currentPage - 1,
//                    nextKey = currentPage + 1
//                )
//            }
//
//            val response = popularMoviesRepository.getPopularMoviesPerPage(currentPage)
//            val data = response.body()?.popularMovies ?: emptyList()
//
//            // Cache the loaded data
//            cache[currentPage] = data
//
//            return LoadResult.Page(
//                data = data,
//                prevKey = if (currentPage == initialPage) null else currentPage - 1,
//                nextKey = if (data.isNotEmpty()) currentPage + 1 else null
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }

//        try {
//            val currentPage = params.key ?: 1
//
//            return if (currentPage == 1) {
//                // Make the API call only for the first page
//                loadPage(currentPage)
//            } else {
//                // Check if the next page is the one expected to load
//                if (currentPage == nextPageToLoad) {
//                    loadPage(currentPage)
//                } else {
//                    // Return empty data for other pages (they will be loaded as you scroll)
//                    LoadResult.Page(emptyList(), null, null)
//                }
//            }
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }


//        try {
//            val currentPage = params.key ?: initialPage
//
//            if (currentPage == initialPage) {
//                // Load the first page only if it hasn't been loaded yet
//                 mutex.withLock{
//                    if (!isFirstPageLoaded && !isLoading) {
//                        isLoading = true
//                        val response = popularMoviesRepository.getPopularMoviesPerPage(currentPage)
//                        val data = response.body()?.popularMovies ?: emptyList()
//                        isFirstPageLoaded = true
//                        isLoading = false
//
//                        return LoadResult.Page(
//                            data = data,
//                            prevKey = null,
//                            nextKey = if (data.isNotEmpty()) currentPage + 1 else null
//                        )
//                    }
//                }
//            }
//
//            return if (isLoading) {
//                // Return loading state if another page is currently loading
//                return LoadResult.Error(Exception("Loading in progress"))
//            } else {
//                // Implement logic to load other pages
//                // ...
//
//                // Return data for subsequent pages
//                val response = popularMoviesRepository.getPopularMoviesPerPage(currentPage)
//                val data = response.body()?.popularMovies ?: emptyList()
//                LoadResult.Page(
//                    data = data/* Your loaded data */,
//                    prevKey = if (currentPage == initialPage) null else currentPage - 1,
//                    nextKey = currentPage.plus(1)/* Calculate next page key */
//                )
//            }
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
    }

//    private suspend fun loadPage(page: Int): LoadResult<Int, PopularMoviesModel> {
//        if (isCurrentlyLoading) {
//            // API call is already in progress for another page
//            return LoadResult.Error(Exception("Loading in progress"))
//        }
//
//        return try {
//            isCurrentlyLoading = true
//            val response = popularMoviesRepository.getPopularMoviesPerPage(page)
//            val data = response.body()?.popularMovies ?: emptyList()
//            nextPageToLoad = page + 1
//            isCurrentlyLoading = false
//            LoadResult.Page(
//                data = data,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (data.isNotEmpty()) page + 1 else null
//            )
//        } catch (e: Exception) {
//            isCurrentlyLoading = false
//            LoadResult.Error(e)
//        }
//    }

    override fun getRefreshKey(state: PagingState<Int, PopularMoviesModel>): Int? {
        //
        return null

//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
    }
}