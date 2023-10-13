package com.example.tmdb.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.DetailsScreenBinding
import com.example.tmdb.model.TrendingTVShowsDetailsResponse
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.viewModel.PopularMoviesViewModel
import com.example.tmdb.viewModel.PopularMoviesViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

class DetailsScreen : AppCompatActivity() {
    private lateinit var dbinding: DetailsScreenBinding
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbinding = DetailsScreenBinding.inflate(layoutInflater)
        setContentView(dbinding.root)
        val popularMoviesService: PopularMoviesService = RetrofitClient.service
        val popularMoviesRepository = PopularMoviesRepository(popularMoviesService)
        popularMoviesViewModel = ViewModelProvider(
            this, PopularMoviesViewModelFactory(popularMoviesRepository)
        )[PopularMoviesViewModel::class.java]
        dbinding.toolbar.apply {
            navigationIcon = ContextCompat.getDrawable(
                this@DetailsScreen, R.drawable.back_button
            )
            setNavigationOnClickListener {
                finish()
            }
        }

        val getMovieId = intent.extras!!.getInt("movie_id")
        Log.d("DS", "movie_id: $getMovieId")

        popularMoviesViewModel.getMoviesDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "movieDetails:$detailsResponse")
            val backdropPath = "https://image.tmdb.org/t/p/w1280"
            val posterPath = "https://image.tmdb.org/t/p/w500"
            dbinding.movieTitle.text = detailsResponse.title
            dbinding.movieBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
            dbinding.popularMovieIv.load("$posterPath${detailsResponse.posterPath}")
            dbinding.movieOriginalTitle.text = detailsResponse.originalTitle
            dbinding.movieTagline.text = detailsResponse.tagline
            dbinding.movieOverview.text = detailsResponse.overview
            val voteAverageCount = detailsResponse.voteAverage
            val roundedAvgCount = (voteAverageCount!! * 10).roundToInt() / 10.0
            dbinding.movieAvgVote.text = roundedAvgCount.toString()
            dbinding.movieLanguage.text = detailsResponse.originalLanguage!!.uppercase()
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

            dbinding.movieGenres.text = "$releaseYear, ${myGenres.joinToString(", ")}"

        }

        popularMoviesViewModel.getMoviesDetails(getMovieId)


    }
}