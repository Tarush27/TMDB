package com.example.tmdb.utils

enum class TimeWindow {
    DAY, WEEK
}

fun TimeWindow.stringAbc() = when(this){
    TimeWindow.DAY -> "day"
    TimeWindow.WEEK -> "week"
}