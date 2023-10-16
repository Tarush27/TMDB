package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var popularMovies: ArrayList<PopularMoviesModel> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("total_results") var totalResults: Int
)