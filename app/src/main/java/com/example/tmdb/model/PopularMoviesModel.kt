package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesModel(
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("id") var popularMovieId: Int? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("title") var popularMovieTitle: String? = null
)
