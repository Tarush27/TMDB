package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class TrendingTVShowsDetailsResponse(

    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genres") var genres: ArrayList<TrendingTVShowsGenres> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_name") var originalName: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("first_air_date") var releaseDate: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null
)

data class TrendingTVShowsGenres(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)