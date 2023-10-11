package com.example.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapter.PopularMoviesAdapter
import com.example.tmdb.adapter.TopRatedMoviesAdapter
import com.example.tmdb.adapter.TrendingMoviesAdapter
import com.example.tmdb.adapter.TrendingTVShowsAdapter
import com.example.tmdb.adapter.UpcomingMoviesAdapter
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
    private var topRatedMoviesAdapter = TopRatedMoviesAdapter()
    private var upComingMoviesAdapter = UpcomingMoviesAdapter()
    private var trendingMoviesAdapter = TrendingMoviesAdapter()
    private var trendingTVShowsAdapter = TrendingTVShowsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeScreenActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.homeScreenAllPopularTv.setOnClickListener {
            val intent = Intent(this, ScreensActivity::class.java)
            intent.putExtra("screenType", "Popular")
            startActivity(intent)
        }
        binding.homeScreenAllTopRatedTv.setOnClickListener {
            val intent = Intent(this, ScreensActivity::class.java)
            intent.putExtra("screenType", "Top rated")
            startActivity(intent)
        }
        binding.homeScreenAllUpcomingTv.setOnClickListener {
            val intent = Intent(this, ScreensActivity::class.java)
            intent.putExtra("screenType", "Upcoming")
            startActivity(intent)
        }
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
        setupTopRatedMoviesRv()
        setupUpcomingMoviesRv()
        setupTrendingMoviesDayWeekRv()
        setupTrendingTVShowsDayWeekRv()
        popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
            val popularMovies = response.popularMovies
            Log.d("HSA", "popularMovies:$popularMovies")
            popularMoviesAdapter.updatePopularMoviesList(popularMovies)
        }
        popularMoviesViewModel.getTopRatedMoviesResponse.observe(this) { response ->
            val topRatedMovies = response.popularMovies
            Log.d("HSA", "topRatedMovies:$topRatedMovies")
            topRatedMoviesAdapter.updateTopRatedMoviesList(topRatedMovies)

        }

        popularMoviesViewModel.getUpComingMovies.observe(this) { response ->
            val upComingMovies = response.popularMovies
            Log.d("HSA", "upComingMovies:$upComingMovies")
            upComingMoviesAdapter.updateUpcomingMoviesList(upComingMovies)

        }

        popularMoviesViewModel.getTrendingMovies.observe(this) { response ->
            val trendingMovies = response.popularMovies
            Log.d("HSAtrending", "trendingMovies:$trendingMovies")
            trendingMoviesAdapter.updateTrendingMoviesList(trendingMovies)

        }

        popularMoviesViewModel.getTrendingMovies(false)
        binding.trendingMoviesDayWeekSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.trendingMoviesDayWeekSwitch.textOn = "Week"
                popularMoviesViewModel.getTrendingMovies(true)
                popularMoviesViewModel.getTrendingMovies.observe(this) {
                    trendingMoviesAdapter.updateTrendingMoviesList(it.popularMovies)
                }
            } else {
                popularMoviesViewModel.getTrendingMovies(false)
                popularMoviesViewModel.getTrendingMovies.observe(this) {
                    trendingMoviesAdapter.updateTrendingMoviesList(it.popularMovies)
                }
            }
        }

        popularMoviesViewModel.getTrendingTVShows(false)

        popularMoviesViewModel.getTrendingTVShows.observe(this) { response ->
            val trendingTVShows = response.trendingTVShows
            Log.d("HSAtrendingtvshow", "trendingTVShows:$trendingTVShows")
            trendingTVShowsAdapter.updateTrendingTVShowsList(trendingTVShows)

        }
        binding.trendingTVShowsDayWeekSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.trendingTVShowsDayWeekSwitch.textOn = "Week"
                popularMoviesViewModel.getTrendingTVShows(true)
                popularMoviesViewModel.getTrendingTVShows.observe(this) {
                    trendingTVShowsAdapter.updateTrendingTVShowsList(it.trendingTVShows)
                }
            } else {
                popularMoviesViewModel.getTrendingTVShows(false)
                popularMoviesViewModel.getTrendingTVShows.observe(this) {
                    trendingTVShowsAdapter.updateTrendingTVShowsList(it.trendingTVShows)
                }
            }
        }

        popularMoviesViewModel.getTopRatedMovies()
        popularMoviesViewModel.getPopularMovies()
        popularMoviesViewModel.getUpComingMovies()

    }

    private fun setupPopularMoviesRv() {
        binding.popularMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.popularMoviesHorizontalRv.adapter = popularMoviesAdapter
    }

    private fun setupTopRatedMoviesRv() {
        binding.topRatedMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.topRatedMoviesHorizontalRv.adapter = topRatedMoviesAdapter
    }

    private fun setupUpcomingMoviesRv() {
        binding.upcomingMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.upcomingMoviesHorizontalRv.adapter = upComingMoviesAdapter
    }

    private fun setupTrendingMoviesDayWeekRv() {
        trendingMoviesAdapter = TrendingMoviesAdapter()
        binding.trendingMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.trendingMoviesHorizontalRv.adapter = trendingMoviesAdapter
    }

    private fun setupTrendingTVShowsDayWeekRv() {
        trendingTVShowsAdapter = TrendingTVShowsAdapter()
        binding.trendingTVShowsHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.trendingTVShowsHorizontalRv.adapter = trendingTVShowsAdapter
    }
}