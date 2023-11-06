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
import com.example.tmdb.databinding.DetailsScreenBinding
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

class DetailsScreen : AppCompatActivity() {
    private lateinit var detailsScreenBinding: DetailsScreenBinding
    private lateinit var moviesViewModel: MoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsScreenBinding = DetailsScreenBinding.inflate(layoutInflater)
        setContentView(detailsScreenBinding.root)
        val moviesService: MoviesService = RetrofitClient.service
        val movieDatabase = (application as MovieApplication).movieDatabase
        val moviesRepository =
            MoviesRepository(moviesService, movieDatabase, this)
        moviesViewModel = ViewModelProvider(
            this, MoviesViewModelFactory(moviesRepository)
        )[MoviesViewModel::class.java]

        detailsScreenBinding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@DetailsScreen, R.drawable.back_button
            )
            setNavigationOnClickListener {
                finish()
            }
            setPadding(0, getStatusBarHeight(), 0, 0)
        }

        val getMovieId = intent.extras!!.getLong("movie_id")
        val type = intent.extras!!.getString("type")
        Log.d("DS", "movie_id: $getMovieId")
        Log.d("DS", "type: $type")

        moviesViewModel.getMoviesDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "movieDetails:$detailsResponse")
            if (detailsResponse == null) {
                initNoDataFoundScreen()
            } else {
                initMoviesDetails(
                    detailsResponse
                )
            }

        }

        moviesViewModel.getMoviesDetails(getMovieId, type)


    }

    private fun initNoDataFoundScreen() {
        detailsScreenBinding.noInternet.root.visibility = View.VISIBLE
        detailsScreenBinding.popularMoviesMcv.visibility = View.GONE
    }

    private fun initMoviesDetails(
        detailsResponse: MoviesAndTv,
    ) {
        detailsScreenBinding.movieTitle.text = detailsResponse.title
        detailsScreenBinding.movieBackdropIv.load("${MoviesUtils.MOVIE_DETAILS_SCREEN_BASE_BACKDROP_PATH}${detailsResponse.backdropPath}")
        detailsScreenBinding.popularMovieIv.load("${MoviesUtils.BASE_POSTER_PATH}${detailsResponse.posterPath}")
        detailsScreenBinding.movieOriginalTitle.text = detailsResponse.originalTitle
        detailsScreenBinding.movieTagline.text = detailsResponse.tagline
        detailsScreenBinding.movieOverview.text = detailsResponse.overview
        val voteAverageCount = detailsResponse.voteAverage
        Log.d("DSvote", "voteAvgCount:$voteAverageCount")
        val roundedAvgCount = (voteAverageCount?.times(10))?.roundToInt()?.div(10.0)
        Log.d("DSvote", "roundedAvgCount:$roundedAvgCount")
        detailsScreenBinding.movieAvgVote.text = roundedAvgCount.toString()
        detailsScreenBinding.movieLanguage.text = detailsResponse.orgLanguage!!.uppercase()
        val releaseDate = detailsResponse.releaseDate.toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(releaseDate)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        val releaseYear = calendar.get(Calendar.YEAR)
        Log.d("ds", "releaseYear: $releaseYear")
        val genres = detailsResponse.genres

        detailsScreenBinding.movieGenres.text = "$releaseYear, $genres"

        if (detailsResponse.tagline!!.isEmpty()) {
            detailsScreenBinding.movieTagline.visibility = View.GONE
        } else {
            detailsScreenBinding.movieTagline.visibility = View.VISIBLE
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }
}