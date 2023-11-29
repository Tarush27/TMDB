package com.example.tmdb.utils

import com.example.tmdb.BuildConfig

class MoviesUtils {
    companion object {
        const val API_ACCESS_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjU5MGJlOGY4ODFlYmNlMTRiNDA4OGUwODEyNGQ4NCIsInN1YiI6IjY1MWY5OGM0NWIxMjQwMDBlM2QzNTNmMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ok7w39V6uCeBukqnaeSTJt1KdWKPE5e-uFF7rqXZWY0"
        const val API_KEY = BuildConfig.API_KEY

        const val MOVIE_DETAILS_SCREEN_BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/original"
        const val TRENDING_TV_SHOWS_DETAILS_SCREEN_BASE_BACKDROP_PATH =
            "https://image.tmdb.org/t/p/w1280"
        const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"
        const val MOVIES_PAGING_SCREEN_BASE_POSTER_PATH = "https://image.tmdb.org/t/p/original"
        const val NOW_PLAYING_MOVIES_BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
        const val NOW_PLAYING_MOVIES_BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w500"
    }
}

