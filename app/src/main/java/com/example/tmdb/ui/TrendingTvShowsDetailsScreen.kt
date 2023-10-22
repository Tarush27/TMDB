package com.example.tmdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.ActivityTrendingTvShowsDetailsScreenBinding
import com.example.tmdb.databinding.DetailsScreenBinding
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
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
        val popularMoviesRepository = PopularMoviesRepository(popularMoviesService)
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
        }
        val getTrendingTvShowId = intent.extras!!.getInt("trending_tv_show_id")
        Log.d("TTVDS", "trending_tv_show_id: $getTrendingTvShowId")

        popularMoviesViewModel.getTrendingTvShowsDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "movieDetails:$detailsResponse")
            val backdropPath = "https://image.tmdb.org/t/p/w1280"
            val posterPath = "https://image.tmdb.org/t/p/w500"
            binding.trendingTvShowsTitle.text = detailsResponse.name
            binding.trendingTvShowsBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
            binding.trendingTvShowsIv.load("$posterPath${detailsResponse.posterPath}")
            binding.trendingTvShowsOriginalTitle.text = detailsResponse.originalName
            binding.trendingTvShowsTagline.text = detailsResponse.tagline
            binding.trendingTvShowsOverview.text = detailsResponse.overview
            val voteAverageCount = detailsResponse.voteAverage
            val roundedAvgCount = (voteAverageCount!! * 10).roundToInt() / 10.0
            binding.movieAvgVote.text = roundedAvgCount.toString()
            binding.trendingTvShowsLanguage.text = detailsResponse.originalLanguage!!.uppercase()
            val releaseDate = detailsResponse.releaseDate.toString()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.parse(releaseDate)
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            val releaseYear = calendar.get(Calendar.YEAR)
            Log.d("ds", "releaseYear: $releaseYear")
            val genres = detailsResponse.genres
            val myGenres = arrayListOf<String>()
            for (genre in genres.indices) {
                val genreName = genres[genre].name
                myGenres.add(genreName!!)
            }

            binding.trendingTvShowsGenres.text = "$releaseYear, ${myGenres.joinToString(", ")}"

        }

        popularMoviesViewModel.getTrendingTVShowsDetails(getTrendingTvShowId)
    }
}