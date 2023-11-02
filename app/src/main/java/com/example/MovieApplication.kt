package com.example

import android.app.Application
import android.util.Log
import com.example.tmdb.room.MovieDb
import com.example.tmdb.utils.SharedPrefsUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private const val IS_OFFLINE_ENABLED = "is_offline_enabled"
private const val IS_TRENDING_ENABLED = "trending_enabled"

class MovieApplication : Application() {
    lateinit var movieDatabase: MovieDb
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    override fun onCreate() {
        super.onCreate()
        movieDatabase = MovieDb.getDatabase(applicationContext)
        firebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("app", "successful fetch")
                val isOfflineEnabled = firebaseRemoteConfig.getBoolean(IS_OFFLINE_ENABLED)
                Log.d("app", "onCreate: isOfflineEnabled $isOfflineEnabled")
                SharedPrefsUtils.setIsOfflineEnabled(isOfflineEnabled, applicationContext)

                val isTrendingEnabled = firebaseRemoteConfig.getBoolean(IS_TRENDING_ENABLED)
                Log.d("app", "onCreate: isTrendingEnabled $isTrendingEnabled")
                SharedPrefsUtils.setIsTrendingEnabled(isTrendingEnabled, applicationContext)
            } else {
                Log.d("app", "un-successful fetch")
            }
//            val isOfflineEnabled = firebaseRemoteConfig.getBoolean(IS_OFFLINE_ENABLED)
//            Log.d("app", "onCreate: isOfflineEnabled $isOfflineEnabled")
//            SharedPrefsUtils.setIsOfflineEnabled(isOfflineEnabled, applicationContext)
//
//            val isTrendingEnabled = firebaseRemoteConfig.getBoolean(IS_TRENDING_ENABLED)
//            Log.d("app", "onCreate: isTrendingEnabled $isTrendingEnabled")
//            SharedPrefsUtils.setIsTrendingEnabled(isTrendingEnabled, applicationContext)
        }
    }

//    private fun isOfflineEnabled(): Boolean {
//        val c = firebaseRemoteConfig.getBoolean(IS_OFFLINE_ENABLED)
//        Log.d("app", "onCreate: isOfflineEnabled $c")
//        return c
//    }



}