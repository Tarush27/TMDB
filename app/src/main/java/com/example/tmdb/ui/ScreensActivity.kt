package com.example.tmdb.ui

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.MovieApplication
import com.example.tmdb.R
import com.example.tmdb.adapter.MoviesPagingAdapter
import com.example.tmdb.databinding.ActivityScreensBinding
import com.example.tmdb.networking.MoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.utils.ScreenTypes
import com.example.tmdb.utils.popularScreen
import com.example.tmdb.utils.topRatedScreen
import com.example.tmdb.utils.upComingScreen
import com.example.tmdb.viewModel.MoviesViewModel
import com.example.tmdb.viewModel.MoviesViewModelFactory
import kotlinx.coroutines.launch

class ScreensActivity : BaseThemeActivity() {
    private lateinit var binding: ActivityScreensBinding
    private lateinit var moviesPagingAdapter: MoviesPagingAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreensBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val moviesService: MoviesService = RetrofitClient.service
        val movieDatabase = (application as MovieApplication).movieDatabase
        val moviesRepository =
            MoviesRepository(moviesService, movieDatabase, this)
        moviesViewModel = ViewModelProvider(
            this, MoviesViewModelFactory(moviesRepository)
        )[MoviesViewModel::class.java]

        binding.baseToolbar.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@ScreensActivity, R.drawable.back_button
            )
            setNavigationOnClickListener {
                finish()
            }
        }
        when (intent.getStringExtra("screenType")) {
            ScreenTypes.POPULAR.popularScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    title = getString(R.string.popular)
                }

                setupMoviesRecyclerView()
                lifecycleScope.launch {
                    moviesViewModel.getPopularMoviesPageWise()
                        .collect {
                            moviesPagingAdapter.submitData(lifecycle, it)
                        }

                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        binding.prgBarMovies.isVisible = it.source.append is LoadState.Loading
                    }
                }

            }

            ScreenTypes.TOP_RATED.topRatedScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    title = getString(R.string.top_rated)
                }
                setupMoviesRecyclerView()
                lifecycleScope.launch {
                    moviesViewModel.getTopRatedMoviesPageWise().collect {
                        moviesPagingAdapter.submitData(lifecycle, it)
                    }
                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        binding.prgBarMovies.isVisible = it.source.append is LoadState.Loading
                    }

                }
            }

            ScreenTypes.UPCOMING.upComingScreen() -> {
                binding.baseToolbar.toolbar.apply {
                    title = getString(R.string.upcoming)
                }
                setupMoviesRecyclerView()

                lifecycleScope.launch {
                    moviesViewModel.getUpcomingMoviesPageWise().collect {
                        moviesPagingAdapter.submitData(lifecycle, it)
                    }
                }

                lifecycleScope.launch {
                    moviesPagingAdapter.loadStateFlow.collect {
                        binding.prgBarMovies.isVisible = it.source.append is LoadState.Loading
                    }
                }
            }
        }

    }

    private fun setupMoviesRecyclerView() {
        moviesPagingAdapter = MoviesPagingAdapter()
        if (intent.getStringExtra("screenType") == ScreenTypes.POPULAR.popularScreen()) {
            moviesPagingAdapter.setMovieType(ScreenTypes.POPULAR.popularScreen())
        } else if (intent.getStringExtra("screenType") == ScreenTypes.TOP_RATED.topRatedScreen()) {
            moviesPagingAdapter.setMovieType(ScreenTypes.TOP_RATED.topRatedScreen())
        } else if (intent.getStringExtra("screenType") == ScreenTypes.UPCOMING.upComingScreen()) {
            moviesPagingAdapter.setMovieType(ScreenTypes.UPCOMING.upComingScreen())
        }
        binding.screensHorizontalRv.layoutManager = GridLayoutManager(this, 2)

        binding.screensHorizontalRv.adapter = moviesPagingAdapter
    }
}