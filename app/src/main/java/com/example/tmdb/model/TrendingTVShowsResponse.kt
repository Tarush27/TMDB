package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class TrendingTVShowsResponse(
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var trendingTVShows: ArrayList<TrendingTVShowsModel> = arrayListOf()
)
