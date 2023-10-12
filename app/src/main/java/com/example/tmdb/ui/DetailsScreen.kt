package com.example.tmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tmdb.R
import com.example.tmdb.databinding.DetailsScreenBinding

class DetailsScreen : AppCompatActivity() {
    private lateinit var dbinding: DetailsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbinding = DetailsScreenBinding.inflate(layoutInflater)
        setContentView(dbinding.root)

        dbinding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@DetailsScreen, R.drawable.back_button
            )
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}