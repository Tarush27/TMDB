package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.SingleTrendingMovieDayWeekBinding
import com.example.tmdb.model.MoviesModel
import com.example.tmdb.ui.DetailsScreen
import com.example.tmdb.utils.MoviesUtils

class TrendingMoviesAdapter :
    RecyclerView.Adapter<TrendingMoviesAdapter.TrendingMoviesItemViewHolder>() {
    inner class TrendingMoviesItemViewHolder(val binding: SingleTrendingMovieDayWeekBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val trendingMovies: ArrayList<MoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TrendingMoviesItemViewHolder {
        val binding =
            SingleTrendingMovieDayWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrendingMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = trendingMovies.size

    override fun onBindViewHolder(holder: TrendingMoviesItemViewHolder, position: Int) {
        val popularMovie = trendingMovies[position]
        with(holder) {
            with(popularMovie) {
                binding.trendingMoviesTv.text = this.popularMovieTitle
                binding.trendingMoviesIv.load("${MoviesUtils.BASE_POSTER_PATH}${this.posterPath}") {
                    try {
                        error(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_connection_error
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

//                    error(
//                        ContextCompat.getDrawable(
//                            itemView.context,
//                            R.drawable.ic_connection_error
//                        )
//                    )

                }
                binding.trendingMoviesMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putLong("movie_id", this.popularMovieId!!)
                    bundle.putString("type", "Trending Movies")
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("trendingmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateTrendingMoviesList(updatedPopularMovies: ArrayList<MoviesModel>) {
        trendingMovies.clear()
        trendingMovies.addAll(updatedPopularMovies)
        notifyDataSetChanged()
    }
}