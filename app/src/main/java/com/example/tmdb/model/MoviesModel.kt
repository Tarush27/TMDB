package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    @SerializedName("id") var popularMovieId: Long? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("title") var popularMovieTitle: String? = null
)
