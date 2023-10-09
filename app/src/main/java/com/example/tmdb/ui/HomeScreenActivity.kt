package com.example.tmdb.ui

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapter.PopularMoviesAdapter
import com.example.tmdb.databinding.HomeScreenActivityBinding
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory

class HomeScreenActivity : BaseThemeActivity() {
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private lateinit var binding: HomeScreenActivityBinding
    private var popularMoviesAdapter = PopularMoviesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val popularMoviesService: PopularMoviesService = RetrofitClient.service
        val popularMoviesRepository = PopularMoviesRepository(popularMoviesService)
        popularMoviesViewModel = ViewModelProvider(
            this, PopularMoviesViewModelFactory(popularMoviesRepository)
        )[PopularMoviesViewModel::class.java]
        binding.baseToolbar.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@HomeScreenActivity, R.drawable.hamburger
            )
            title = context.getString(R.string.home_screen_toolbar_title)
            menuInflater.inflate(R.menu.search_menu, menu)
        }
        setupPopularMoviesRv()
//        getAndShowMovies()
//        lifecycleScope.launch {
//            val result = withContext(Dispatchers.Main) {
//                popularMoviesViewModel.getPopularMovies()
//            }
//        }

        popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
            val popularMovies = response.popularMovies
            Log.d("HSA", "popularMovies:$popularMovies")
            popularMoviesAdapter.updatePopularMoviesList(popularMovies)
        }


        popularMoviesViewModel.getPopularMovies()

    }

    private fun getAndShowMovies() {
        popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
            response.popularMovies
        }
    }

    private fun setupPopularMoviesRv() {
        binding.popularMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.popularMoviesHorizontalRv.adapter = popularMoviesAdapter
    }
}