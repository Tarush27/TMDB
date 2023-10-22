package com.example.tmdb.ui

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapter.LoadMoreAdapter
import com.example.tmdb.adapter.MoviesPagingAdapter
import com.example.tmdb.databinding.ActivityScreensBinding
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.popularScreen
import com.example.tmdb.utils.topRatedScreen
import com.example.tmdb.utils.upComingScreen
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory
import kotlinx.coroutines.launch

class ScreensActivity : BaseThemeActivity() {
    private lateinit var binding: ActivityScreensBinding

    //    private var moviesAdapter = MoviesAdapter()
    private lateinit var moviesPagingAdapter: MoviesPagingAdapter
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
//        setupPopularMoviesRv()

        moviesPagingAdapter = MoviesPagingAdapter()
        binding.screensHorizontalRv.layoutManager = GridLayoutManager(this, 2)

        binding.screensHorizontalRv.adapter = moviesPagingAdapter
        binding.screensHorizontalRv.adapter = moviesPagingAdapter.withLoadStateFooter(
            LoadMoreAdapter()
        )
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

//                setupPopularMoviesRv()

                lifecycleScope.launch {
                    Log.d("screensactivity", "before observe: observed...")
                    popularMoviesViewModel.popularMoviesList.collect {
                        moviesPagingAdapter.submitData(lifecycle, it)
                    }
                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        val state = it.refresh
                        binding.prgBarMovies.isVisible = state is LoadState.Loading
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
//                setupPopularMoviesRv()
                lifecycleScope.launch {
                    Log.d("screensactivity", "before observe: observed...")
                    popularMoviesViewModel.topRatedMoviesList.collect {
                        moviesPagingAdapter.submitData(lifecycle, it)
                    }
                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        val state = it.refresh
                        binding.prgBarMovies.isVisible = state is LoadState.Loading
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

                lifecycleScope.launch {
                    Log.d("screensactivity", "before observe: observed...")
                    popularMoviesViewModel.upComingMoviesList.collect {
                        moviesPagingAdapter.submitData(lifecycle, it)
                    }
                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        val state = it.refresh
                        binding.prgBarMovies.isVisible = state is LoadState.Loading
                    }
                }
            }
        }

    }

    private fun setupPopularMoviesRv() {
//        moviesAdapter = MoviesAdapter()
//        moviesPagingAdapter = MoviesPagingAdapter()
//        binding.screensHorizontalRv.layoutManager = GridLayoutManager(this, 2)
//
//        binding.screensHorizontalRv.adapter = moviesPagingAdapter
//        binding.screensHorizontalRv.adapter = moviesPagingAdapter.withLoadStateFooter(
//            LoadMoreAdapter()
//        )
    }
}