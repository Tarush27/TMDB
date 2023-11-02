package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var popularMovies: List<PopularMoviesModel> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
)