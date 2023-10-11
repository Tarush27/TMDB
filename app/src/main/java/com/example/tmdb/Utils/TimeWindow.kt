package com.example.tmdb.Utils

enum class TimeWindow {
    DAY, WEEK
}

fun TimeWindow.stringAbc() = when(this){
    TimeWindow.DAY -> "day"
    TimeWindow.WEEK -> "week"
}