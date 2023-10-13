package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenPopularMovieSectionItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen
import java.io.File

class PopularMoviesAdapter :
    RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesItemViewHolder>() {
    inner class PopularMoviesItemViewHolder(val binding: HomeScreenPopularMovieSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val popularMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesItemViewHolder {
        val binding =
            HomeScreenPopularMovieSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PopularMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = popularMovies.size

    override fun onBindViewHolder(holder: PopularMoviesItemViewHolder, position: Int) {
        val popularMovie = popularMovies[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(popularMovie) {
                binding.popularMovieTv.text = this.popularMovieTitle
                binding.popularMovieIv.load("$posterPath${this.posterPath}")
                binding.popularMoviesMcv.setOnClickListener {
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

    fun updatePopularMoviesList(updatedPopularMovies: ArrayList<PopularMoviesModel>) {
        popularMovies.addAll(updatedPopularMovies.subList(0, 10))
        notifyDataSetChanged()
    }
}