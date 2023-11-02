package com.example.tmdb.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tmdb.model.DetailsResponse
import com.example.tmdb.model.Genres
import com.example.tmdb.model.NowPlayingMoviesModel
import com.example.tmdb.model.NowPlayingMoviesResponse
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.model.PopularMoviesResponse
import com.example.tmdb.model.TrendingTVShowsDetailsResponse
import com.example.tmdb.model.TrendingTVShowsGenres
import com.example.tmdb.model.TrendingTVShowsModel
import com.example.tmdb.model.TrendingTVShowsResponse
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.paging.ScreensPagingSource
import com.example.tmdb.room.MovieDb
import com.example.tmdb.room.MoviesAndTv
import com.example.tmdb.utils.NetworkState
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.SharedPrefsUtils
import com.example.tmdb.utils.TimeWindow
import com.example.tmdb.utils.stringAbc
import kotlinx.coroutines.flow.Flow

class PopularMoviesRepository(
    private val popularMoviesService: PopularMoviesService,
    private val movieDb: MovieDb,
    private val applicationContext: Context,
) {

    private val _popularMoviesResponse = MutableLiveData<PopularMoviesResponse?>()
    val popularMoviesResponse: MutableLiveData<PopularMoviesResponse?> = _popularMoviesResponse

    private val _topRatedMoviesResponse = MutableLiveData<PopularMoviesResponse?>()
    val topRatedMoviesResponse: MutableLiveData<PopularMoviesResponse?> = _topRatedMoviesResponse

    private val _upComingMoviesResponse = MutableLiveData<PopularMoviesResponse?>()
    val upComingMoviesResponse: MutableLiveData<PopularMoviesResponse?> = _upComingMoviesResponse

    private val _trendingMoviesResponse = MutableLiveData<PopularMoviesResponse?>()
    val trendingMoviesResponse: MutableLiveData<PopularMoviesResponse?> = _trendingMoviesResponse

    private val _trendingTVShowsResponse = MutableLiveData<TrendingTVShowsResponse?>()
    val trendingTVShowsResponse: MutableLiveData<TrendingTVShowsResponse?> =
        _trendingTVShowsResponse

    private val _movieDetailsResponse = MutableLiveData<MoviesAndTv?>()
    val movieDetailsResponse: MutableLiveData<MoviesAndTv?> = _movieDetailsResponse

    private val _trendingTVShowsDetailsResponse = MutableLiveData<MoviesAndTv?>()
    val trendingTVShowsDetailsResponse: MutableLiveData<MoviesAndTv?> =
        _trendingTVShowsDetailsResponse

    private val _nowPlayingMoviesResponse = MutableLiveData<NowPlayingMoviesResponse?>()
    val nowPlayingMoviesResponse: MutableLiveData<NowPlayingMoviesResponse?> =
        _nowPlayingMoviesResponse

    private fun showEmptyResponseMessage(message: String) {
        // Show a toast message indicating that there's no data available
        // You can customize the message based on your requirements
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun showExceptionMessage(message: String?) {
        // Show a toast message indicating an error occurred
        // You can customize the message based on your requirements
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }


    suspend fun getPopularMovies() {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getPopularMoviesResponse()
                Log.d("repo", "getPopularMovies: ${response.body()!!.popularMovies}")
                if (response.body() != null) {
                    val moviesAndTvShowsList = response.body()!!.popularMovies.map {
                        MoviesAndTv(
                            id = it.popularMovieId,
                            posterPath = it.posterPath,
                            title = it.popularMovieTitle!!,
                            movieType = "Popular"
                        )
                    }

//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
//                        Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getPopularMovies: is offline enabled false")
//
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _popularMoviesResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
//                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                Log.d("repo", "getPopularMovies: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _popularMoviesResponse.value = null
//                    return
//                }
                val popularMoviesListFromDb = movieDb.roomDao().getMoviesByType("Popular")
                Log.d("repo", "getPopularMoviesFromDb: $popularMoviesListFromDb")
                if (popularMoviesListFromDb.isNotEmpty()) {
                    val popularMoviesModels = popularMoviesListFromDb.map {
                        PopularMoviesModel(
                            popularMovieId = it.id,
                            posterPath = it.posterPath,
                            popularMovieTitle = it.title
                        )
                    }

                    val popularMoviesResponse = PopularMoviesResponse(
                        page = null,
                        popularMovies = popularMoviesModels,
                        totalPages = null,
                        totalResults = null
                    )
                    _popularMoviesResponse.value = popularMoviesResponse
                } else {
                    _popularMoviesResponse.value = null
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun getPopularMoviesPerPage(): Flow<PagingData<PopularMoviesModel>> =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(
                popularMoviesService, ScreenTypes.POPULAR, movieDb, applicationContext
            )
        }.flow

    fun getTopRatedMoviesPerPage(): Flow<PagingData<PopularMoviesModel>> =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(
                popularMoviesService, ScreenTypes.TOP_RATED, movieDb, applicationContext
            )
        }.flow

    fun getUpcomingMoviesPerPage(): Flow<PagingData<PopularMoviesModel>> =
        Pager(PagingConfig(pageSize = 1, enablePlaceholders = false)) {
            ScreensPagingSource(
                popularMoviesService, ScreenTypes.UPCOMING, movieDb, applicationContext
            )
        }.flow


    /*popularMoviesService.getPopularMoviesResponsePerPage(page)*/

//    suspend fun getTopRatedMoviesPerPage(page: Int) =
//        popularMoviesService.getTopRatedMoviesResponsePerPage(page)
//
//    suspend fun getUpcomingMoviesPerPage(page: Int) =
//        popularMoviesService.getUpcomingMoviesResponsePerPage(page)

    suspend fun getTopRatedMovies() {

        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getTopRatedMoviesResponse()
                if (response.body() != null) {
                    val moviesAndTvShowsList = response.body()!!.popularMovies.map {
                        MoviesAndTv(
                            id = it.popularMovieId,
                            posterPath = it.posterPath,
                            title = it.popularMovieTitle!!,
                            movieType = "Top rated"
                        )
                    }


//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
//                        Log.d("repo", "moviesAndTvShowsList offline true: $moviesAndTvShowsList")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getTopRatedMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _topRatedMoviesResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
//                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                Log.d("repo", "getTopRatedMovies: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _topRatedMoviesResponse.value = null
//                    return
//                }
                val topRatedMoviesListFromDb = movieDb.roomDao().getMoviesByType(type = "Top rated")
                Log.d("repo", "getTopRatedMoviesFromDb: $topRatedMoviesListFromDb")
                if (topRatedMoviesListFromDb.isNotEmpty()) {
                    val popularMoviesModels = topRatedMoviesListFromDb.map {
                        PopularMoviesModel(
                            popularMovieId = it.id,
                            posterPath = it.posterPath,
                            popularMovieTitle = it.title
                        )
                    }

                    val topRatedMoviesResponse = PopularMoviesResponse(
                        page = null,
                        popularMovies = popularMoviesModels,
                        totalPages = null,
                        totalResults = null
                    )
                    _topRatedMoviesResponse.value = topRatedMoviesResponse
                } else {
                    showEmptyResponseMessage("details fetch failed")
                    _topRatedMoviesResponse.value = null
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    suspend fun getUpcomingMovies() {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getUpcomingMoviesResponse()
                if (response.body() != null) {
                    val moviesAndTvShowsList = response.body()!!.popularMovies.map {
                        MoviesAndTv(
                            id = it.popularMovieId,
                            posterPath = it.posterPath,
                            title = it.popularMovieTitle!!,
                            movieType = "Upcoming"
                        )
                    }
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
//                        Log.d("repo", "moviesAndTvShowsList offline true: $moviesAndTvShowsList")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getUpcomingMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _upComingMoviesResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getUpcomingMovies: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _upComingMoviesResponse.value = null
//                    return
//                }
                val upcomingMoviesListFromDb = movieDb.roomDao().getMoviesByType(type = "Upcoming")
                Log.d("repo", "getUpcomingMoviesFromDb: $upcomingMoviesListFromDb")
                if (upcomingMoviesListFromDb.isNotEmpty()) {
                    val popularMoviesModels = upcomingMoviesListFromDb.map {
                        PopularMoviesModel(
                            popularMovieId = it.id,
                            posterPath = it.posterPath,
                            popularMovieTitle = it.title
                        )
                    }

                    val upcomingMoviesResponse = PopularMoviesResponse(
                        page = null,
                        popularMovies = popularMoviesModels,
                        totalPages = null,
                        totalResults = null
                    )
                    _upComingMoviesResponse.value = upcomingMoviesResponse
                } else {
                    _upComingMoviesResponse.value = null
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getTrendingMovies(type: String) {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getTrendingMoviesResponse(type)
                if (response.body() != null) {
                    val isDay = if (TimeWindow.DAY.stringAbc() == type) 1 else 0
                    val isWeek = if (TimeWindow.WEEK.stringAbc() == type) 1 else 0
                    val moviesAndTvShowsList = response.body()!!.popularMovies.map {
                        MoviesAndTv(
                            id = it.popularMovieId,
                            posterPath = it.posterPath,
                            title = it.popularMovieTitle!!,
                            movieType = "Trending Movies",
                            isDay = isDay,
                            isWeek = isWeek

                        )
                    }
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
////                        Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getPopularMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _trendingMoviesResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getTrendingMoviesDayWise: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _trendingMoviesResponse.value = null
//                    return
//                }
                if (TimeWindow.DAY.stringAbc() == type) {
                    val trendingMoviesListDayWise = movieDb.roomDao().getTrendingMoviesDayWise(
                        type = "Trending Movies", isDay = 1
                    )
                    Log.d("repo", "getTrendingMoviesListDayWise: $trendingMoviesListDayWise")

                    if (trendingMoviesListDayWise.isNotEmpty()) {
                        val popularMoviesModels = trendingMoviesListDayWise.map {
                            PopularMoviesModel(
                                popularMovieId = it.id,
                                posterPath = it.posterPath,
                                popularMovieTitle = it.title
                            )
                        }

                        val trendingMoviesResponseDayWise = PopularMoviesResponse(
                            page = null,
                            popularMovies = popularMoviesModels,
                            totalPages = null,
                            totalResults = null
                        )
                        _trendingMoviesResponse.value = trendingMoviesResponseDayWise
                    } else {
//                        showEmptyResponseMessage("trending movies day wise fetch failed")
                        _trendingMoviesResponse.value = null
                    }
                } else if (TimeWindow.WEEK.stringAbc() == type) {
                    val trendingMoviesListWeekWise = movieDb.roomDao().getTrendingMoviesWeekWise(
                        type = "Trending Movies", isWeek = 1
                    )
                    Log.d("repo", "getTrendingMoviesListWeekWise: $trendingMoviesListWeekWise")

                    if (trendingMoviesListWeekWise.isNotEmpty()) {
                        val popularMoviesModels = trendingMoviesListWeekWise.map {
                            PopularMoviesModel(
                                popularMovieId = it.id,
                                posterPath = it.posterPath,
                                popularMovieTitle = it.title
                            )
                        }

                        val trendingMoviesResponseWeekWise = PopularMoviesResponse(
                            page = null,
                            popularMovies = popularMoviesModels,
                            totalPages = null,
                            totalResults = null
                        )
                        _trendingMoviesResponse.value = trendingMoviesResponseWeekWise
                    } else {
//                        showEmptyResponseMessage("trending movies week wise fetch failed")
                        _trendingMoviesResponse.value = null
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    suspend fun getTrendingTVShows(type: String) {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getTrendingTVShowsResponse(type)
                if (response.body() != null) {
                    val isDay = if (TimeWindow.DAY.stringAbc() == type) 1 else 0
                    val isWeek = if (TimeWindow.WEEK.stringAbc() == type) 1 else 0
                    val moviesAndTvShowsList = response.body()!!.trendingTVShows.map {
                        MoviesAndTv(
                            id = it.trendingTVShowId,
                            posterPath = it.trendingTVShowPosterPath,
                            title = it.trendingTVShowName!!,
                            movieType = "Trending TV Shows",
                            isDay = isDay,
                            isWeek = isWeek
                        )
                    }
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
////                        Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getPopularMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _trendingTVShowsResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                showExceptionMessage(e.message)
            }

        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getTrendingTVShowsDayWise: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _trendingTVShowsResponse.value = null
//                    return
//                }
                if (TimeWindow.DAY.stringAbc() == type) {
                    val trendingTVShowsListFromDbDayWise = movieDb.roomDao()
                        .getTrendingTVShowsDayWise(type = "Trending TV Shows", isDay = 1)
                    Log.d("repo", "getTrendingTVShowsFromDb: $trendingTVShowsListFromDbDayWise")
                    if (trendingTVShowsListFromDbDayWise.isNotEmpty()) {
                        val trendingTVShowsModels = trendingTVShowsListFromDbDayWise.map {
                            TrendingTVShowsModel(
                                trendingTVShowId = it.id,
                                trendingTVShowPosterPath = it.posterPath,
                                trendingTVShowName = it.title
                            )
                        }

                        val trendingTVShowsResponseDayWise = TrendingTVShowsResponse(
                            page = null,
                            trendingTVShows = trendingTVShowsModels as ArrayList<TrendingTVShowsModel>
                        )
                        _trendingTVShowsResponse.value = trendingTVShowsResponseDayWise
                    } else {
//                        showEmptyResponseMessage("trending tv shows day wise fetch failed")
                        _trendingTVShowsResponse.value = null
                    }
                } else if (TimeWindow.WEEK.stringAbc() == type) {
                    val trendingTVShowsListFromDbWeekWise = movieDb.roomDao()
                        .getTrendingTVShowsWeekWise(type = "Trending TV Shows", isWeek = 1)
                    Log.d("repo", "getTrendingTVShowsFromDb: $trendingTVShowsListFromDbWeekWise")
                    if (trendingTVShowsListFromDbWeekWise.isNotEmpty()) {
                        val trendingTVShowsModels = trendingTVShowsListFromDbWeekWise.map {
                            TrendingTVShowsModel(
                                trendingTVShowId = it.id,
                                trendingTVShowPosterPath = it.posterPath,
                                trendingTVShowName = it.title
                            )
                        }

                        val trendingTVShowsResponseWeekWise = TrendingTVShowsResponse(
                            page = null,
                            trendingTVShows = trendingTVShowsModels as ArrayList<TrendingTVShowsModel>
                        )
                        _trendingTVShowsResponse.value = trendingTVShowsResponseWeekWise
                    } else {
//                        showEmptyResponseMessage("trending tv shows week wise fetch failed")
                        _trendingTVShowsResponse.value = null
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    suspend fun getMoviesDetails(id: Long, type: String?) {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getMoviesDetails(id)
                if (response.body() != null) {
                    val detailsResponse = response.body()!!
                    val genres = detailsResponse.genres
                    val myGenres = arrayListOf<String>()
                    for (genre in genres.indices) {
                        val genreName = genres[genre].name
                        myGenres.add(genreName!!)
                    }
                    Log.d("repo", "getMoviesDetailsRemote: $detailsResponse")
                    val moviesDetail = MoviesAndTv(
                        id = detailsResponse.id,
                        posterPath = detailsResponse.posterPath,
                        title = detailsResponse.title!!,
                        backdropPath = detailsResponse.backdropPath,
                        overview = detailsResponse.overview,
                        orgLanguage = detailsResponse.originalLanguage,
                        releaseDate = detailsResponse.releaseDate,
                        tagline = detailsResponse.tagline,
                        voteAverage = detailsResponse.voteAverage,
                        movieType = type,
                        originalTitle = detailsResponse.originalTitle,
                        genres = myGenres.joinToString(", ")
                    )
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
////                        Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                        movieDb.roomDao().insertMoviesAndTvDetails(moviesDetail)
//                    } else {
//                        Log.d("repo", "getPopularMovies: is offline enabled false")
//                    }

                    movieDb.roomDao().insertMoviesAndTvDetails(moviesDetail)
                    _movieDetailsResponse.value = moviesDetail
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getMoviesDetails: isOfflineEnabled: $isOfflineEnabled")
                var moviesDetails = movieDb.roomDao().getMoviesAndTvDetails(id)
//                if (!isOfflineEnabled) {
//                    moviesDetails = movieDb.roomDao().getMoviesAndTvDetails(null)
//                    Log.d("repo", "getMoviesDetails: ${moviesDetails?.voteAverage}")
//                    if (moviesDetails?.voteAverage == null) {
//                        _movieDetailsResponse.value = null
//                    }
//                }
                Log.d("repo", "getMoviesDetailsFromDb: $moviesDetails")
                val genresString = moviesDetails?.genres
                val genresList = genresString?.split(", ") ?: emptyList()
                val detailsResponse = DetailsResponse(id = moviesDetails?.id,
                    backdropPath = moviesDetails?.backdropPath,
                    originalLanguage = moviesDetails?.orgLanguage,
                    overview = moviesDetails?.overview,
                    posterPath = moviesDetails?.posterPath,
                    releaseDate = moviesDetails?.releaseDate,
                    tagline = moviesDetails?.tagline,
                    title = moviesDetails?.title,
                    voteAverage = moviesDetails?.voteAverage,
                    originalTitle = moviesDetails?.originalTitle,
                    genres = genresList.map {
                        Genres(name = it)
                    } as ArrayList<Genres>)
                Log.d("repo", "getMoviesResponseFromDb: $detailsResponse")
                if (moviesDetails!!.voteAverage == null) {
                    _movieDetailsResponse.value = null
                } else {
                    _movieDetailsResponse.value = moviesDetails
                }
//                _movieDetailsResponse.value = detailsResponse
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    suspend fun getTrendingTVShowsDetails(id: Long, trendingTvShows: String?) {
        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getTrendingTvShowsDetails(id)
                if (response.body() != null) {
                    val trendingTvShowsDetailsResponse = response.body()!!
                    Log.d(
                        "repo", "getTrendingTVShowsDetailsRemote: $trendingTvShowsDetailsResponse"
                    )
                    val genres = trendingTvShowsDetailsResponse.genres
                    val myGenres = arrayListOf<String>()
                    for (genre in genres.indices) {
                        val genreName = genres[genre].name
                        myGenres.add(genreName!!)
                    }
                    val trendingTvShowsDetails = MoviesAndTv(
                        id = trendingTvShowsDetailsResponse.id,
                        posterPath = trendingTvShowsDetailsResponse.posterPath,
                        title = trendingTvShowsDetailsResponse.name!!,
                        backdropPath = trendingTvShowsDetailsResponse.backdropPath,
                        overview = trendingTvShowsDetailsResponse.overview,
                        orgLanguage = trendingTvShowsDetailsResponse.originalLanguage,
                        releaseDate = trendingTvShowsDetailsResponse.releaseDate,
                        tagline = trendingTvShowsDetailsResponse.tagline,
                        voteAverage = trendingTvShowsDetailsResponse.voteAverage,
                        movieType = trendingTvShows,
                        originalTitle = trendingTvShowsDetailsResponse.originalName,
                        genres = myGenres.joinToString(", ")
                    )
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
////                        Log.d("repo", "isOfflineEnabledafter: $isOfflineEnabled")
//                        movieDb.roomDao().insertMoviesAndTvDetails(trendingTvShowsDetails)
//                    } else {
//                        Log.d("repo", "getPopularMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMoviesAndTvDetails(trendingTvShowsDetails)
                    _trendingTVShowsDetailsResponse.value = trendingTvShowsDetails
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getTrendingTVShowsDetails: isOfflineEnabled: $isOfflineEnabled")
                var trendingTVShowsDetails = movieDb.roomDao().getMoviesAndTvDetails(id)
//                if (!isOfflineEnabled) {
//                    trendingTVShowsDetails = movieDb.roomDao().getMoviesAndTvDetails(null)
//                    Log.d("repo", "getMoviesDetails: ${trendingTVShowsDetails?.voteAverage}")
//                    if (trendingTVShowsDetails?.voteAverage == null) {
//                        _movieDetailsResponse.value = null
//                    }
//                }
                val genresString = trendingTVShowsDetails?.genres
                val genresList = genresString?.split(", ") ?: emptyList()
                val trendingTVShowsDetailsResponse =
                    TrendingTVShowsDetailsResponse(id = trendingTVShowsDetails?.id,
                        backdropPath = trendingTVShowsDetails?.backdropPath,
                        originalLanguage = trendingTVShowsDetails?.orgLanguage,
                        overview = trendingTVShowsDetails?.overview,
                        posterPath = trendingTVShowsDetails?.posterPath,
                        releaseDate = trendingTVShowsDetails?.releaseDate,
                        tagline = trendingTVShowsDetails?.tagline,
                        name = trendingTVShowsDetails?.title,
                        voteAverage = trendingTVShowsDetails?.voteAverage,
                        originalName = trendingTVShowsDetails?.originalTitle,
                        genres = genresList.map {
                            TrendingTVShowsGenres(name = it)
                        } as ArrayList<TrendingTVShowsGenres>)
                Log.d("repo", "getTrendingTVShowsDetailsFromDb: $trendingTVShowsDetailsResponse")
                if (trendingTVShowsDetails?.voteAverage == null) {
                    _trendingTVShowsDetailsResponse.value = null
                } else {
                    _trendingTVShowsDetailsResponse.value = trendingTVShowsDetails
                }
//                _trendingTVShowsDetailsResponse.value = trendingTVShowsDetailsResponse

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    suspend fun getNowPlayingMoviesDetails() {

        if (NetworkState.isInternetAvailable(applicationContext)) {
            try {
                val response = popularMoviesService.getNowPlayingMoviesResponse()
                if (response.body() != null) {
                    val moviesAndTvShowsList = response.body()!!.nowPlayingMovies.map {
                        MoviesAndTv(
                            id = it.nowPlayingMovieId,
                            posterPath = it.posterPath,
                            title = it.nowPlayingMovieTitle!!,
                            backdropPath = it.backdrop_path,
                            overview = it.nowPlayingMovieOverview,
                            movieType = "Now Playing"
                        )
                    }
//                    val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
//                    if (isOfflineEnabled) {
//                        Log.d("repo", "moviesAndTvShowsList offline enabled: $isOfflineEnabled")
//                        movieDb.roomDao().insertMovies(moviesAndTvShowsList)
//                    } else {
//                        Log.d("repo", "getNowPlayingMovies: is offline enabled false")
//                    }
                    movieDb.roomDao().insertMovies(moviesAndTvShowsList)
                    _nowPlayingMoviesResponse.value = response.body()
                } else {
                    showEmptyResponseMessage("")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                val isOfflineEnabled = SharedPrefsUtils.getIsOfflineEnabled(applicationContext)
                Log.d("repo", "getNowPlayingMovies: isOfflineEnabled: $isOfflineEnabled")
//                if (!isOfflineEnabled) {
//                    _nowPlayingMoviesResponse.value = null
//                    return
//                }
                val nowPlayingMoviesListFromDb =
                    movieDb.roomDao().getMoviesByType(type = "Now Playing")
                Log.d("repo", "getNowPlayingMoviesDetailsFromDb: $nowPlayingMoviesListFromDb")
                if (nowPlayingMoviesListFromDb.isNotEmpty()) {
                    val nowPlayingMovies = nowPlayingMoviesListFromDb.map {
                        NowPlayingMoviesModel(
                            posterPath = it.posterPath,
                            nowPlayingMovieTitle = it.title,
                            backdrop_path = it.backdropPath,
                            nowPlayingMovieOverview = it.overview
                        )
                    }

                    val nowPlayingMoviesResponse = NowPlayingMoviesResponse(
                        page = null,
                        nowPlayingMovies = nowPlayingMovies as ArrayList<NowPlayingMoviesModel>
                    )
                    _nowPlayingMoviesResponse.value = nowPlayingMoviesResponse
                } else {
//                    showEmptyResponseMessage("now playing movies details fetched failed")
                    _nowPlayingMoviesResponse.value = null
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}