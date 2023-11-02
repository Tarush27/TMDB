package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class TrendingTVShowsModel(
    @SerializedName("id") var trendingTVShowId: Long? = null,
    @SerializedName("poster_path") var trendingTVShowPosterPath: String? = null,
    @SerializedName("original_name") var trendingTVShowName: String? = null
)
