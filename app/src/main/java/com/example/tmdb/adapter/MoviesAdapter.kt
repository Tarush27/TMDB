package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.ScreensItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.PopularMoviesItemViewHolder>() {
    inner class PopularMoviesItemViewHolder(val binding: ScreensItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val movies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesItemViewHolder {
        val binding =
            ScreensItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PopularMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: PopularMoviesItemViewHolder, position: Int) {
        val movie = movies[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(movie) {
                binding.screensTv.text = this.popularMovieTitle
                binding.screensIv.load("$posterPath${this.posterPath}")
                binding.screensMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movie_id", this.popularMovieId!!)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("popmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateMoviesList(updatedMovies: ArrayList<PopularMoviesModel>) {
        movies.clear()
        movies.addAll(updatedMovies)
        notifyDataSetChanged()
    }
}