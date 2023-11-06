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
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.getStatusBarHeight
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

class TrendingTvShowsDetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTrendingTvShowsDetailsScreenBinding
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrendingTvShowsDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val popularMoviesService: PopularMoviesService = RetrofitClient.service
        val movieDatabase = (application as MovieApplication).movieDatabase
        val popularMoviesRepository =
            PopularMoviesRepository(popularMoviesService, movieDatabase, this)
        popularMoviesViewModel = ViewModelProvider(
            this, PopularMoviesViewModelFactory(popularMoviesRepository)
        )[PopularMoviesViewModel::class.java]
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

        popularMoviesViewModel.getTrendingTvShowsDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "trendingTvShowsDetails:$detailsResponse")
            if (detailsResponse == null) {
                binding.noInternet.root.visibility = View.VISIBLE
                binding.trendingTvShowsMcv.visibility = View.GONE
            } else {
                val backdropPath = "https://image.tmdb.org/t/p/w1280"
                val posterPath = "https://image.tmdb.org/t/p/w500"
                binding.trendingTvShowsTitle.text = detailsResponse.title
                binding.trendingTvShowsBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
                binding.trendingTvShowsIv.load("$posterPath${detailsResponse.posterPath}")
                binding.trendingTvShowsOriginalTitle.text = detailsResponse.originalTitle
                binding.trendingTvShowsTagline.text = detailsResponse.tagline
                binding.trendingTvShowsOverview.text = detailsResponse.overview
                val voteAverageCount = detailsResponse.voteAverage
                val roundedAvgCount = (voteAverageCount?.times(10))?.roundToInt()?.div(10.0)
                binding.trendingTvShowsAvgVote.text = roundedAvgCount.toString()
                binding.trendingTvShowsLanguage.text =
                    detailsResponse.orgLanguage!!.uppercase()
                val releaseDate = detailsResponse.releaseDate.toString()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = dateFormat.parse(releaseDate)
                val calendar = Calendar.getInstance()
                calendar.time = date!!
                val releaseYear = calendar.get(Calendar.YEAR)
                Log.d("ds", "releaseYear: $releaseYear")
                val genres = detailsResponse.genres
//                val myGenres = arrayListOf<String>()
//                for (genre in genres.indices) {
//                    val genreName = genres[genre].name
//                    myGenres.add(genreName!!)
//                }

                binding.trendingTvShowsGenres.text = "$releaseYear, $genres"
                if (detailsResponse.tagline!!.isEmpty()) {
                    binding.trendingTvShowsTagline.visibility = View.GONE
                } else {
                    binding.trendingTvShowsTagline.visibility = View.VISIBLE
                }
            }
//            val backdropPath = "https://image.tmdb.org/t/p/w1280"
//            val posterPath = "https://image.tmdb.org/t/p/w500"
//            binding.trendingTvShowsTitle.text = detailsResponse!!.name
//            binding.trendingTvShowsBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
//            binding.trendingTvShowsIv.load("$posterPath${detailsResponse.posterPath}")
//            binding.trendingTvShowsOriginalTitle.text = detailsResponse.originalName
//            binding.trendingTvShowsTagline.text = detailsResponse.tagline
//            binding.trendingTvShowsOverview.text = detailsResponse.overview
//            val voteAverageCount = detailsResponse.voteAverage
//            val roundedAvgCount = (voteAverageCount?.times(10))?.roundToInt()?.div(10.0)
//            binding.trendingTvShowsAvgVote.text = roundedAvgCount.toString()
//            binding.trendingTvShowsLanguage.text = detailsResponse.originalLanguage!!.uppercase()
//            val releaseDate = detailsResponse.releaseDate.toString()
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val date = dateFormat.parse(releaseDate)
//            val calendar = Calendar.getInstance()
//            calendar.time = date!!
//            val releaseYear = calendar.get(Calendar.YEAR)
//            Log.d("ds", "releaseYear: $releaseYear")
//            val genres = detailsResponse.genres
//            val myGenres = arrayListOf<String>()
//            for (genre in genres.indices) {
//                val genreName = genres[genre].name
//                myGenres.add(genreName!!)
//            }
//
//            binding.trendingTvShowsGenres.text = "$releaseYear, ${myGenres.joinToString(", ")}"

        }

        popularMoviesViewModel.getTrendingTVShowsDetails(getTrendingTvShowId, trendingTvShows)
    }

    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }
}