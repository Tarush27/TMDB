package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class NowPlayingMoviesResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var nowPlayingMovies: ArrayList<NowPlayingMoviesModel> = arrayListOf()
)
