package com.example.tmdb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class BaseContext : AppCompatActivity() {
    init {
        instance = this
    }

    companion object {
        private var instance: BaseContext? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}