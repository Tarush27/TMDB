package com.example.tmdb

import android.annotation.SuppressLint

@SuppressLint("StaticFieldLeak")
object BaseContextSingleton {
    val context = BaseContext.applicationContext()
}