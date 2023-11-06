package com.example.tmdb.utils

import android.content.Context

object SharedPrefsUtils {
    private const val FILE_NAME = "firebase_remote_configs_info"
    fun setIsOfflineEnabled(value: Boolean, context: Context) {
        val mPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = mPrefs.edit()
        editor.putBoolean("is_offline_enabled", value)
        editor.apply()
    }

    fun getIsOfflineEnabled(context: Context): Boolean {
        val mPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        return mPrefs.getBoolean("is_offline_enabled", true)
    }


    fun setIsTrendingEnabled(value: Boolean, context: Context) {
        val mPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = mPrefs.edit()
        editor.putBoolean("is_trending_enabled", value)
        editor.apply()
    }

    fun getIsTrendingEnabled(context: Context): Boolean {
        val mPrefs = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        return mPrefs.getBoolean("is_trending_enabled", true)
    }

}