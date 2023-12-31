package com.example.tmdb.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("moviesAndTv")
data class MoviesAndTv(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo("posterPath") var posterPath: String? = null,
    @ColumnInfo("title") var title: String? = null,
    @ColumnInfo("backdropPath") var backdropPath: String? = null,
    @ColumnInfo("overview") var overview: String? = null,
    @ColumnInfo("orgLanguage") var orgLanguage: String? = null,
    @ColumnInfo("releaseDate") var releaseDate: String? = null,
    @ColumnInfo("tagline") var tagline: String? = null,
    @ColumnInfo("voteAverage") var voteAverage: Double? = null,
    @ColumnInfo("movieType") var movieType: String? = null,
    @ColumnInfo("isDay") var isDay: Int = 0,
    @ColumnInfo("isWeek") var isWeek: Int = 0,
    @ColumnInfo("orgTitle") var originalTitle: String? = null,
    @ColumnInfo("genre") var genres: String? = null,
)
