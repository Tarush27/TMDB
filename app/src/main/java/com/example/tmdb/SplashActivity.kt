package com.example.tmdb

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.tmdb.ui.BaseThemeActivity
import com.example.tmdb.ui.HomeScreenActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseThemeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeScreenActivity::class.java))
            finish()
        }, 2000)
    }
}