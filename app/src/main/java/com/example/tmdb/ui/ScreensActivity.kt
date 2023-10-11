package com.example.tmdb.ui

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.Utils.ScreenTypes
import com.example.tmdb.Utils.popularScreen
import com.example.tmdb.Utils.topRatedScreen
import com.example.tmdb.Utils.upComingScreen
import com.example.tmdb.adapter.MoviesAdapter
import com.example.tmdb.databinding.ActivityScreensBinding
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory

class ScreensActivity : BaseThemeActivity() {
    private lateinit var binding: ActivityScreensBinding
    private var moviesAdapter = MoviesAdapter()
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreensBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val popularMoviesService: PopularMoviesService = RetrofitClient.service
        val popularMoviesRepository = PopularMoviesRepository(popularMoviesService)
        popularMoviesViewModel = ViewModelProvider(
            this, PopularMoviesViewModelFactory(popularMoviesRepository)
        )[PopularMoviesViewModel::class.java]
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
                setupPopularMoviesRv()
                popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
                    val popularMovies = response.popularMovies
                    Log.d("SA", "popularMovies:$popularMovies")
                    moviesAdapter.updateMoviesList(popularMovies)
                }
                popularMoviesViewModel.getPopularMovies()
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
                setupPopularMoviesRv()
                popularMoviesViewModel.getTopRatedMoviesResponse.observe(this) { response ->
                    val topRatedMovies = response.popularMovies
                    Log.d("SA", "topRatedMovies:$topRatedMovies")
                    moviesAdapter.updateMoviesList(topRatedMovies)
                }
                popularMoviesViewModel.getTopRatedMovies()
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
                setupPopularMoviesRv()
                popularMoviesViewModel.getUpComingMovies.observe(this) { response ->
                    val upComingMovies = response.popularMovies
                    Log.d("HSA", "upComingMovies:$upComingMovies")
                    moviesAdapter.updateMoviesList(upComingMovies)

                }
                popularMoviesViewModel.getUpComingMovies()
            }
        }

    }

    private fun setupPopularMoviesRv() {
        moviesAdapter = MoviesAdapter()
        binding.screensHorizontalRv.layoutManager = GridLayoutManager(this, 2)

        binding.screensHorizontalRv.adapter = moviesAdapter
    }
}