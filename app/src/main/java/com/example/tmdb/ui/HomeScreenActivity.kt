package com.example.tmdb.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.tmdb.R
import com.example.tmdb.databinding.HomeScreenActivityBinding

class HomeScreenActivity : BaseThemeActivity() {
    private lateinit var binding: HomeScreenActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.baseToolbar.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@HomeScreenActivity,
                R.drawable.hamburger
            )
            title = context.getString(R.string.home_screen_toolbar_title)
            menuInflater.inflate(R.menu.search_menu, menu)
        }

    }
}