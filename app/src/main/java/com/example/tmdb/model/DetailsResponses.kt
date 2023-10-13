package com.example.tmdb.model

import com.google.gson.annotations.SerializedName

data class DetailsResponse(

    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("tagline") var tagline: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null
)

data class Genres(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)