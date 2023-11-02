package com.example.tmdb.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MoviesAndTv>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesAndTvDetails(movies: MoviesAndTv)

    @Query("select * from moviesAndTv where movieType =:type")
    suspend fun getMoviesByType(type: String): List<MoviesAndTv>

    @Query("select * from moviesAndTv where id=:movieId")
    suspend fun getMoviesAndTvDetails(movieId: kotlin.Long?): MoviesAndTv?

    @Query("select * from moviesAndTv where movieType =:type and isDay=:isDay")
    suspend fun getTrendingMoviesDayWise(
        type: String,
        isDay: Int,
    ): List<MoviesAndTv>

    @Query("select * from moviesAndTv where movieType =:type and isWeek=:isWeek")
    suspend fun getTrendingMoviesWeekWise(
        type: String,
        isWeek: Int,
    ): List<MoviesAndTv>

    @Query("select * from moviesAndTv where movieType =:type and isDay=:isDay")
    suspend fun getTrendingTVShowsDayWise(
        type: String,
        isDay: Int,
    ): List<MoviesAndTv>

    @Query("select * from moviesAndTv where movieType =:type and isWeek=:isWeek")
    suspend fun getTrendingTVShowsWeekWise(
        type: String,
        isWeek: Int,
    ): List<MoviesAndTv>

}