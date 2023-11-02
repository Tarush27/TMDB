package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesModel(
    @SerializedName("id") var nowPlayingMovieId: Long? = null,
    @SerializedName("backdrop_path") var backdrop_path: String? = null,
    @SerializedName("overview") var nowPlayingMovieOverview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("title") var nowPlayingMovieTitle: String? = null,
)
