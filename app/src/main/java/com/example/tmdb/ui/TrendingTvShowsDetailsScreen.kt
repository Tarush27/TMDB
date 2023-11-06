package com.example.tmdb.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.MovieApplication
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTrendingTvShowsDetailsScreenBinding
import com.example.tmdb.networking.MoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.MoviesRepository
import com.example.tmdb.room.MoviesAndTv
import com.example.tmdb.utils.MoviesUtils
import com.example.tmdb.utils.getStatusBarHeight
import com.example.tmdb.viewModel.MoviesViewModel
import com.example.tmdb.viewModel.MoviesViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

class TrendingTvShowsDetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingTvShowsDetailsScreenBinding
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingTvShowsDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesService: MoviesService = RetrofitClient.service
        val movieDatabase = (application as MovieApplication).movieDatabase
        val moviesRepository =
            MoviesRepository(moviesService, movieDatabase, this)
        moviesViewModel = ViewModelProvider(
            this, MoviesViewModelFactory(moviesRepository)
        )[MoviesViewModel::class.java]
        binding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@TrendingTvShowsDetailsScreen, R.drawable.back_button
            )
            setNavigationOnClickListener {
                finish()
            }
            setPadding(0, getStatusBarHeight(), 0, 0)
        }
        val getTrendingTvShowId = intent.extras!!.getLong("trending_tv_show_id")
        Log.d("TTVDS", "trending_tv_show_id: $getTrendingTvShowId")

        val trendingTvShows = intent.extras!!.getString("type")
        Log.d("TTVDS", "trendingTvShows: $trendingTvShows")

        moviesViewModel.getTrendingTvShowsDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "trendingTvShowsDetails:$detailsResponse")
            if (detailsResponse == null) {
                binding.noInternet.root.visibility = View.VISIBLE
                binding.trendingTvShowsMcv.visibility = View.GONE
            } else {
                initTrendingTvShowsDetails(detailsResponse)
            }

        }

        moviesViewModel.getTrendingTVShowsDetails(getTrendingTvShowId, trendingTvShows)
    }

    private fun initTrendingTvShowsDetails(detailsResponse: MoviesAndTv) {
        binding.trendingTvShowsTitle.text = detailsResponse.title
        binding.trendingTvShowsBackdropIv.load("${MoviesUtils.TRENDING_TV_SHOWS_DETAILS_SCREEN_BASE_BACKDROP_PATH}${detailsResponse.backdropPath}")
        binding.trendingTvShowsIv.load("${MoviesUtils.BASE_POSTER_PATH}${detailsResponse.posterPath}")
        binding.trendingTvShowsOriginalTitle.text = detailsResponse.originalTitle
        binding.trendingTvShowsTagline.text = detailsResponse.tagline
        binding.trendingTvShowsOverview.text = detailsResponse.overview
        val voteAverageCount = detailsResponse.voteAverage
        val roundedAvgCount = (voteAverageCount?.times(10))?.roundToInt()?.div(10.0)
        binding.trendingTvShowsAvgVote.text = roundedAvgCount.toString()
        binding.trendingTvShowsLanguage.text = detailsResponse.orgLanguage!!.uppercase()
        val releaseDate = detailsResponse.releaseDate.toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(releaseDate)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        val releaseYear = calendar.get(Calendar.YEAR)
        Log.d("ds", "releaseYear: $releaseYear")
        val genres = detailsResponse.genres

        binding.trendingTvShowsGenres.text = "$releaseYear, $genres"
        if (detailsResponse.tagline!!.isEmpty()) {
            binding.trendingTvShowsTagline.visibility = View.GONE
        } else {
            binding.trendingTvShowsTagline.visibility = View.VISIBLE
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }
}