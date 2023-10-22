package com.example.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb.R
import com.example.tmdb.adapter.HomeScreenMoviesAdapter
import com.example.tmdb.adapter.NowPlayingMoviesAdapter
import com.example.tmdb.adapter.TrendingMoviesAdapter
import com.example.tmdb.adapter.TrendingTVShowsAdapter
import com.example.tmdb.databinding.HomeScreenActivityBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory


class HomeScreenActivity : BaseThemeActivity() {
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private lateinit var binding: HomeScreenActivityBinding
    private var trendingMoviesAdapter = TrendingMoviesAdapter()
    private var homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
    private var trendingTVShowsAdapter = TrendingTVShowsAdapter()
    private var nowPlayingMoviesAdapter = NowPlayingMoviesAdapter()
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
        setupTrendingMoviesDayWeekRv()
        setupTrendingTVShowsDayWeekRv()
        setupNowPlayingMoviesRv()
        popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
            setupPopularMoviesRv()
            val popularMovies = response.popularMovies
            Log.d("HSApopular", "popularMovies:$popularMovies")
            homeScreenMoviesAdapter.updateHomeScreenMoviesList(popularMovies as ArrayList<PopularMoviesModel>)
        }
        popularMoviesViewModel.getTopRatedMoviesResponse.observe(this) { response ->
            setupTopRatedMoviesRv()
            val topRatedMovies = response.popularMovies
            Log.d("HSAtoprated", "topRatedMovies:$topRatedMovies")
            homeScreenMoviesAdapter.updateHomeScreenMoviesList(topRatedMovies as ArrayList<PopularMoviesModel>)
        }

        popularMoviesViewModel.getUpComingMovies.observe(this) { response ->
            setupUpcomingMoviesRv()
            val upComingMovies = response.popularMovies
            Log.d("HSAupcoming", "upComingMovies:$upComingMovies")
            homeScreenMoviesAdapter.updateHomeScreenMoviesList(upComingMovies as ArrayList<PopularMoviesModel>)
        }

        popularMoviesViewModel.getNowPlayingMoviesDetails.observe(this) { response ->
            val nowPlayingMovies = response.nowPlayingMovies
            Log.d("HSAnowplayingmov", "nowplayingmov:$nowPlayingMovies")
            nowPlayingMoviesAdapter.updateNowPlayingMoviesList(nowPlayingMovies)

        }

        popularMoviesViewModel.getTrendingMovies.observe(this) { response ->
            val trendingMovies = response.popularMovies
            Log.d("HSAtrendingmovies", "trendingMovies:$trendingMovies")
            trendingMoviesAdapter.updateTrendingMoviesList(trendingMovies as ArrayList<PopularMoviesModel>)

        }

        popularMoviesViewModel.getTrendingMovies(false)
        binding.trendingMoviesDayWeekSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.trendingMoviesDayWeekSwitch.textOn = "Week"
                popularMoviesViewModel.getTrendingMovies(true)
                popularMoviesViewModel.getTrendingMovies.observe(this) {
                    trendingMoviesAdapter.updateTrendingMoviesList(it.popularMovies as ArrayList<PopularMoviesModel>)
                }
            } else {
                popularMoviesViewModel.getTrendingMovies(false)
                popularMoviesViewModel.getTrendingMovies.observe(this) {
                    trendingMoviesAdapter.updateTrendingMoviesList(it.popularMovies as ArrayList<PopularMoviesModel>)
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
        popularMoviesViewModel.getNowPlayingMoviesDetails()

    }

    private fun setupPopularMoviesRv() {
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        binding.popularMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.popularMoviesHorizontalRv.adapter = homeScreenMoviesAdapter
    }

    private fun setupTopRatedMoviesRv() {
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        binding.topRatedMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.topRatedMoviesHorizontalRv.adapter = homeScreenMoviesAdapter
    }

    private fun setupUpcomingMoviesRv() {
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        binding.upcomingMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.upcomingMoviesHorizontalRv.adapter = homeScreenMoviesAdapter
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

    private fun setupNowPlayingMoviesRv() {
        nowPlayingMoviesAdapter = NowPlayingMoviesAdapter()
        binding.nowPlayingMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.nowPlayingMoviesHorizontalRv.adapter = nowPlayingMoviesAdapter
    }
}