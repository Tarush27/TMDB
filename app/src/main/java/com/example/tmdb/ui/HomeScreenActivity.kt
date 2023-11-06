package com.example.tmdb.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MovieApplication
import com.example.tmdb.R
import com.example.tmdb.adapter.HomeScreenMoviesAdapter
import com.example.tmdb.adapter.NowPlayingMoviesAdapter
import com.example.tmdb.adapter.TrendingMoviesAdapter
import com.example.tmdb.adapter.TrendingTVShowsAdapter
import com.example.tmdb.databinding.HomeActivityBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.SharedPrefsUtils
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory


class HomeScreenActivity : BaseThemeActivity() {
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel

    //    private lateinit var binding: HomeScreenActivityBinding
    private lateinit var binding: HomeActivityBinding
    private var trendingMoviesAdapter = TrendingMoviesAdapter()
    private var homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
    private var trendingTVShowsAdapter = TrendingTVShowsAdapter()
    private var nowPlayingMoviesAdapter = NowPlayingMoviesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val isTrendingSectionEnabled = SharedPrefsUtils.getIsTrendingEnabled(this)
        if (!isTrendingSectionEnabled) {
            binding.trendingMoviesDayWeekSwitch.visibility = View.GONE
            binding.trendingMoviesHorizontalRv.visibility = View.GONE
            binding.trendingMoviesTv.visibility = View.GONE
            binding.trendingTvShowsDawWeekErrorMsgTv.visibility = View.GONE
            binding.trendingMoviesDawWeekErrorMsgTv.visibility = View.GONE

            binding.trendingTVShowsDayWeekSwitch.visibility = View.GONE
            binding.trendingTVShowsHorizontalRv.visibility = View.GONE
            binding.trendingTVShowsTv.visibility = View.GONE

            binding.trendingMoviesDayWeekText.visibility = View.GONE
            binding.trendingTvShowsDayWeekText.visibility = View.GONE
        }
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
        val movieDatabase = (application as MovieApplication).movieDatabase
        val popularMoviesRepository =
            PopularMoviesRepository(popularMoviesService, movieDatabase, this)
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
        popularMoviesViewModel.getPopularMoviesResponse.observe(this) { response ->
            setupPopularMoviesRv()
            if (response == null) {
//                Log.d("HSAnullresp", "response: $response")
                binding.noInternet.root.visibility = View.VISIBLE
                binding.nestedScrollView.visibility = View.GONE
            } else {
                val popularMovies = response.popularMovies
                Log.d("HSApopular", "popularMovies:$popularMovies")
                homeScreenMoviesAdapter.updateHomeScreenMoviesList(popularMovies as ArrayList<PopularMoviesModel>)
            }
//            val popularMovies = response!!.popularMovies
//
//            Log.d("HSApopular", "popularMovies:$popularMovies")
//            homeScreenMoviesAdapter.updateHomeScreenMoviesList(popularMovies as ArrayList<PopularMoviesModel>)
        }


        popularMoviesViewModel.getTopRatedMoviesResponse.observe(this) { response ->
            setupTopRatedMoviesRv()
            if (response == null) {
                binding.noInternet.root.visibility = View.VISIBLE
                binding.nestedScrollView.visibility = View.GONE
            } else {
                val topRatedMovies = response.popularMovies
                Log.d("HSAtoprated", "topRatedMovies:$topRatedMovies")
                homeScreenMoviesAdapter.updateHomeScreenMoviesList(topRatedMovies as ArrayList<PopularMoviesModel>)
            }
//            val topRatedMovies = response.popularMovies
//            Log.d("HSAtoprated", "topRatedMovies:$topRatedMovies")
//            homeScreenMoviesAdapter.updateHomeScreenMoviesList(topRatedMovies as ArrayList<PopularMoviesModel>)
        }

        popularMoviesViewModel.getUpComingMovies.observe(this) { response ->
            setupUpcomingMoviesRv()
            if (response == null) {
                binding.noInternet.root.visibility = View.VISIBLE
                binding.nestedScrollView.visibility = View.GONE
            } else {
                val upComingMovies = response.popularMovies
                Log.d("HSAupcoming", "upComingMovies:$upComingMovies")
                homeScreenMoviesAdapter.updateHomeScreenMoviesList(upComingMovies as ArrayList<PopularMoviesModel>)
            }
//            val upComingMovies = response.popularMovies
//            Log.d("HSAupcoming", "upComingMovies:$upComingMovies")
//            homeScreenMoviesAdapter.updateHomeScreenMoviesList(upComingMovies as ArrayList<PopularMoviesModel>)
        }

        popularMoviesViewModel.getNowPlayingMoviesDetails.observe(this) { response ->
            setupNowPlayingMoviesRv()
            if (response == null) {
                binding.noInternet.root.visibility = View.VISIBLE
                binding.nestedScrollView.visibility = View.GONE
            } else {
                val nowPlayingMovies = response.nowPlayingMovies
                Log.d("HSAnowplayingmov", "nowplayingmov:$nowPlayingMovies")
                nowPlayingMoviesAdapter.updateNowPlayingMoviesList(nowPlayingMovies)
            }
//            val nowPlayingMovies = response.nowPlayingMovies
//            Log.d("HSAnowplayingmov", "nowplayingmov:$nowPlayingMovies")
//            nowPlayingMoviesAdapter.updateNowPlayingMoviesList(nowPlayingMovies)

        }
        popularMoviesViewModel.getTrendingMovies.observe(this) { response ->
            setupTrendingMoviesDayWeekRv()
            if (response == null) {
                binding.trendingMoviesHorizontalRv.visibility = View.GONE
                val isTrendingMoviesDayWeekSwitchChecked =
                    binding.trendingMoviesDayWeekSwitch.isChecked
                if (isTrendingMoviesDayWeekSwitchChecked) {
                    binding.trendingMoviesDawWeekErrorMsgTv.text =
                        "Trending movies week wise details fetched"
                    binding.trendingMoviesDawWeekErrorMsgTv.visibility = View.VISIBLE
                } else {
                    binding.trendingMoviesDawWeekErrorMsgTv.text =
                        "Trending movies day wise details fetched"
                    binding.trendingMoviesDawWeekErrorMsgTv.visibility = View.VISIBLE
                }


            } else {
                binding.trendingMoviesHorizontalRv.visibility = View.VISIBLE
                binding.trendingMoviesDawWeekErrorMsgTv.text = ""
                binding.trendingMoviesDawWeekErrorMsgTv.visibility = View.GONE
                val trendingMovies = response.popularMovies
                Log.d("HSAtrendingmovies", "trendingMovies:$trendingMovies")
                trendingMoviesAdapter.updateTrendingMoviesList(trendingMovies as ArrayList<PopularMoviesModel>)
            }


        }

        popularMoviesViewModel.getTrendingMovies(false)
        binding.trendingMoviesDayWeekSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.trendingMoviesDayWeekText.text = "Week"
                popularMoviesViewModel.getTrendingMovies(true)

            } else {
                binding.trendingMoviesDayWeekText.text = "Day"
                popularMoviesViewModel.getTrendingMovies(false)

            }
        }


        popularMoviesViewModel.getTrendingTVShows(false)

        popularMoviesViewModel.getTrendingTVShows.observe(this) { response ->
            setupTrendingTVShowsDayWeekRv()
            if (response == null) {
                binding.trendingTVShowsHorizontalRv.visibility = View.GONE

                val isTrendingTVShowsDayWeekSwitchChecked =
                    binding.trendingTVShowsDayWeekSwitch.isChecked
                if (isTrendingTVShowsDayWeekSwitchChecked) {
                    binding.trendingTvShowsDawWeekErrorMsgTv.text =
                        "Trending TV Shows week wise details fetched"
                    binding.trendingTvShowsDawWeekErrorMsgTv.visibility = View.VISIBLE
                } else {
                    binding.trendingTvShowsDawWeekErrorMsgTv.text =
                        "Trending TV Shows day wise details fetched"
                    binding.trendingTvShowsDawWeekErrorMsgTv.visibility = View.VISIBLE
                }

            } else {
                binding.trendingTVShowsHorizontalRv.visibility = View.VISIBLE
                binding.trendingTvShowsDawWeekErrorMsgTv.text = ""
                binding.trendingTvShowsDawWeekErrorMsgTv.visibility = View.GONE
                val trendingTVShows = response.trendingTVShows
                Log.d("HSAtrendingtvshow", "trendingTVShows:$trendingTVShows")
                trendingTVShowsAdapter.updateTrendingTVShowsList(trendingTVShows)
            }


        }
        binding.trendingTVShowsDayWeekSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.trendingTvShowsDayWeekText.text = "Week"
                popularMoviesViewModel.getTrendingTVShows(true)
            } else {
                binding.trendingTvShowsDayWeekText.text = "Day"
                popularMoviesViewModel.getTrendingTVShows(false)

            }
        }

        popularMoviesViewModel.getPopularMovies()
        popularMoviesViewModel.getTopRatedMovies()
        popularMoviesViewModel.getUpComingMovies()
        popularMoviesViewModel.getNowPlayingMoviesDetails()

    }

    private fun setupPopularMoviesRv() {
        val popularMovies = "Popular"
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        homeScreenMoviesAdapter.setMovieType(popularMovies)
        binding.popularMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.popularMoviesHorizontalRv.adapter = homeScreenMoviesAdapter
    }

    private fun setupTopRatedMoviesRv() {
        val topRatedMovies = "Top rated"
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        homeScreenMoviesAdapter.setMovieType(topRatedMovies)
        binding.topRatedMoviesHorizontalRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.topRatedMoviesHorizontalRv.adapter = homeScreenMoviesAdapter
    }

    private fun setupUpcomingMoviesRv() {
        val upcomingMovies = "Upcoming"
        homeScreenMoviesAdapter = HomeScreenMoviesAdapter()
        homeScreenMoviesAdapter.setMovieType(upcomingMovies)
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