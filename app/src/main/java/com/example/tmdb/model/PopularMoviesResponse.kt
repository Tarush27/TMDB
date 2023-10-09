package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var popularMovies: ArrayList<PopularMoviesModel> = arrayListOf()
)