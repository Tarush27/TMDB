package com.example.tmdb.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Rect

const val API_ACCESS_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjU5MGJlOGY4ODFlYmNlMTRiNDA4OGUwODEyNGQ4NCIsInN1YiI6IjY1MWY5OGM0NWIxMjQwMDBlM2QzNTNmMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Ok7w39V6uCeBukqnaeSTJt1KdWKPE5e-uFF7rqXZWY0"

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Activity.getStatusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else Rect().apply { window.decorView.getWindowVisibleDisplayFrame(this) }.top
}