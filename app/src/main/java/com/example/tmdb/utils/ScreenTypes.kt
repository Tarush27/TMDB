package com.example.tmdb.utils

enum class ScreenTypes {
    POPULAR, TOP_RATED, UPCOMING
}

fun ScreenTypes.popularScreen() = this.name.replace("POPULAR", "Popular")
fun ScreenTypes.topRatedScreen() = this.name.replace("TOP_RATED", "Top rated")
fun ScreenTypes.upComingScreen() = this.name.replace("UPCOMING", "Upcoming")