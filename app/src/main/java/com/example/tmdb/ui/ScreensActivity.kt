package com.example.tmdb.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tmdb.R
import com.example.tmdb.Utils.ScreenTypes
import com.example.tmdb.Utils.popularScreen
import com.example.tmdb.adapter.MoviesPagingAdapter
import com.example.tmdb.databinding.ActivityScreensBinding
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ScreensActivity : BaseThemeActivity() {
    private lateinit var binding: ActivityScreensBinding

    //    private var moviesAdapter = MoviesAdapter()
    private var moviesPagingAdapter = MoviesPagingAdapter()
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


//                popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
//                    val popularMovies = response.popularMovies
//                    Log.d("SA", "popularMovies:$popularMovies")
//                    moviesAdapter.updateMoviesList(popularMovies)
//                }
//                popularMoviesViewModel.getPopularMovieList()

                lifecycleScope.launch {
                    popularMoviesViewModel.popularMoviesList.observe(this@ScreensActivity) {
                        moviesPagingAdapter.submitData(lifecycle, it)
                        /*moviesPagingAdapter.addLoadStateListener { loadState ->
                            // show empty list
                            if (loadState.refresh is LoadState.Loading ||
                                loadState.append is LoadState.Loading
                            )
                                binding.prependProgress.isVisible = true
                            else {
                                binding.prependProgress.isVisible = false
                                // If we have an error, show a toast
                                val errorState = when {
                                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                                    else -> null
                                }
                                errorState?.let {
                                    val errorSnackBar =
                                        Snackbar.make(
                                            binding.root,
                                            it.error.toString(),
                                            Snackbar.LENGTH_LONG
                                        )
                                    errorSnackBar.show()

                                }

                            }
                        }*/
                    }
                }
            }

//            ScreenTypes.TOP_RATED.topRatedScreen() -> {
//                binding.baseToolbar.toolbar.apply {
//                    navigationIcon = ContextCompat.getDrawable(
//                        this@ScreensActivity, R.drawable.back_button
//                    )
//                    title = "Top rated"
//                    setNavigationOnClickListener {
//                        finish()
//                    }
//                }
//                setupPopularMoviesRv()
//                popularMoviesViewModel.getTopRatedMoviesResponse.observe(this) { response ->
//                    val topRatedMovies = response.popularMovies
//                    Log.d("SA", "topRatedMovies:$topRatedMovies")
//                    moviesAdapter.updateMoviesList(topRatedMovies)
//                }
//                popularMoviesViewModel.getTopRatedMovies()
//            }

//            ScreenTypes.UPCOMING.upComingScreen() -> {
//                binding.baseToolbar.toolbar.apply {
//                    navigationIcon = ContextCompat.getDrawable(
//                        this@ScreensActivity, R.drawable.back_button
//                    )
//                    title = "Upcoming"
//                    setNavigationOnClickListener {
//                        finish()
//                    }
//                }
//                setupPopularMoviesRv()
//                popularMoviesViewModel.getUpComingMovies.observe(this) { response ->
//                    val upComingMovies = response.popularMovies
//                    Log.d("HSA", "upComingMovies:$upComingMovies")
//                    moviesAdapter.updateMoviesList(upComingMovies)
//
//                }
//                popularMoviesViewModel.getUpComingMovies()
//            }
        }

    }

    private fun setupPopularMoviesRv() {
//        moviesAdapter = MoviesAdapter()
        moviesPagingAdapter = MoviesPagingAdapter()
        /*moviesPagingAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                binding.prependProgress.isVisible = true
            else {
                binding.prependProgress.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    val errorSnackBar =
                        Snackbar.make(
                            binding.root,
                            it.error.toString(),
                            Snackbar.LENGTH_LONG
                        )
                    errorSnackBar.show()

                }

            }
        }*/
        binding.screensHorizontalRv.layoutManager = GridLayoutManager(this, 2)

        binding.screensHorizontalRv.adapter = moviesPagingAdapter
    }
}