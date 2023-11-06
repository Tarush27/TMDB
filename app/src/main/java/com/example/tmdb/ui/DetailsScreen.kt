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
import com.example.tmdb.networking.PopularMoviesService
import com.example.tmdb.networking.RetrofitClient
import com.example.tmdb.repository.PopularMoviesRepository
import com.example.tmdb.utils.getStatusBarHeight
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
        val movieDatabase = (application as MovieApplication).movieDatabase
        val popularMoviesRepository =
            PopularMoviesRepository(popularMoviesService, movieDatabase, this)
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
            setPadding(0, getStatusBarHeight(), 0, 0)
        }

        val getMovieId = intent.extras!!.getLong("movie_id")
        val type = intent.extras!!.getString("type")
        Log.d("DS", "movie_id: $getMovieId")
        Log.d("DS", "type: $type")

        popularMoviesViewModel.getMoviesDetails.observe(this) { detailsResponse ->
            Log.d("DSob", "movieDetails:$detailsResponse")
            if (detailsResponse == null) {
                dbinding.noInternet.root.visibility = View.VISIBLE
                dbinding.popularMoviesMcv.visibility = View.GONE
            } else {
                val backdropPath = "https://image.tmdb.org/t/p/original"
                val posterPath = "https://image.tmdb.org/t/p/w500"
                dbinding.movieTitle.text = detailsResponse.title
                dbinding.movieBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
                dbinding.popularMovieIv.load("$posterPath${detailsResponse.posterPath}")
                dbinding.movieOriginalTitle.text = detailsResponse.originalTitle
                dbinding.movieTagline.text = detailsResponse.tagline
                dbinding.movieOverview.text = detailsResponse.overview
                val voteAverageCount = detailsResponse.voteAverage
                Log.d("DSvote", "voteAvgCount:$voteAverageCount")
                val roundedAvgCount = (voteAverageCount?.times(10))?.roundToInt()?.div(10.0)
                Log.d("DSvote", "roundedAvgCount:$roundedAvgCount")
                dbinding.movieAvgVote.text = roundedAvgCount.toString()
                dbinding.movieLanguage.text = detailsResponse.orgLanguage!!.uppercase()
                val releaseDate = detailsResponse.releaseDate.toString()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = dateFormat.parse(releaseDate)
                val calendar = Calendar.getInstance()
                calendar.time = date!!
                val releaseYear = calendar.get(Calendar.YEAR)
                Log.d("ds", "releaseYear: $releaseYear")
                val genres = detailsResponse.genres
                /*val myGenres = arrayListOf<String>()
                for (genre in genres.indices) {
                    val genreName = genres[genre].name
                    myGenres.add(genreName!!)
                }*/

                dbinding.movieGenres.text = "$releaseYear, $genres"

                if (detailsResponse.tagline!!.isEmpty()) {
                    dbinding.movieTagline.visibility = View.GONE
                }
                else{
                    dbinding.movieTagline.visibility = View.VISIBLE
                }
            }
//            val backdropPath = "https://image.tmdb.org/t/p/original"
//            val posterPath = "https://image.tmdb.org/t/p/w500"
//            dbinding.movieTitle.text = detailsResponse!!.title
//            dbinding.movieBackdropIv.load("$backdropPath${detailsResponse.backdropPath}")
//            dbinding.popularMovieIv.load("$posterPath${detailsResponse.posterPath}")
//            dbinding.movieOriginalTitle.text = detailsResponse.originalTitle
//            dbinding.movieTagline.text = detailsResponse.tagline
//            dbinding.movieOverview.text = detailsResponse.overview
//            val voteAverageCount = detailsResponse.voteAverage
//            val roundedAvgCount = ((voteAverageCount?.times(10))?.roundToInt() ?: 0) / 10.0
//            dbinding.movieAvgVote.text = roundedAvgCount.toString()
//            dbinding.movieLanguage.text = detailsResponse.originalLanguage!!.uppercase()
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
//            dbinding.movieGenres.text = "$releaseYear, ${myGenres.joinToString(", ")}"

//            if (voteAverageCount == 0.0) {
//                dbinding.noInternet.noInternetCl.visibility = View.VISIBLE
//            } else {
//                dbinding.noInternet.noInternetCl.visibility = View.GONE
//            }

        }

        popularMoviesViewModel.getMoviesDetails(getMovieId, type)


    }

    override fun attachBaseContext(newBase: Context?) {
        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }
}