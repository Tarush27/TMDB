package com.example.tmdb.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.tmdb.R
import com.example.tmdb.Utils.ScreenTypes
import com.example.tmdb.Utils.popularScreen
import com.example.tmdb.Utils.topRatedScreen
import com.example.tmdb.Utils.upComingScreen
import com.example.tmdb.databinding.HomeScreenActivityBinding

class ScreensActivity : BaseThemeActivity() {
    private lateinit var binding: HomeScreenActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when (intent.getStringExtra("screenType")) {
            ScreenTypes.POPULAR.popularScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    navigationIcon = ContextCompat.getDrawable(
                        this@ScreensActivity, R.drawable.back_button
                    )
                    title = "Popular"
                    setNavigationOnClickListener {
                        finish()
                    }
                }
            }

            ScreenTypes.TOP_RATED.topRatedScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    navigationIcon = ContextCompat.getDrawable(
                        this@ScreensActivity, R.drawable.back_button
                    )
                    title = "Top rated"
                    setNavigationOnClickListener {
                        finish()
                    }
                }
            }

            ScreenTypes.UPCOMING.upComingScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    navigationIcon = ContextCompat.getDrawable(
                        this@ScreensActivity, R.drawable.back_button
                    )
                    title = "Upcoming"
                    setNavigationOnClickListener {
                        finish()
                    }
                }
            }
        }

    }
}