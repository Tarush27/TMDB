package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesModel(
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("backdrop_path") var backdrop_path: String? = null,
    @SerializedName("title") var nowPlayingMovieTitle: String? = null,
    @SerializedName("overview") var nowPlayingMovieOverview: String? = null
)
