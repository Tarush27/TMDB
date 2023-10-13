package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenTopRatedMoviesSectionItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class TopRatedMoviesAdapter :
    RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesItemViewHolder>() {
    inner class TopRatedMoviesItemViewHolder(val binding: HomeScreenTopRatedMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val topRatedMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedMoviesItemViewHolder {
        val binding =
            HomeScreenTopRatedMoviesSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TopRatedMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = topRatedMovies.size

    override fun onBindViewHolder(holder: TopRatedMoviesItemViewHolder, position: Int) {
        val popularMovie = topRatedMovies[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(popularMovie) {
                binding.topRatedMovieTv.text = this.popularMovieTitle
                binding.topRatedMovieIv.load("$posterPath${this.posterPath}")
                binding.topRatedMoviesMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movie_id", this.popularMovieId!!)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("topratedmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateTopRatedMoviesList(updatedPopularMovies: ArrayList<PopularMoviesModel>) {
        topRatedMovies.addAll(updatedPopularMovies.subList(0, 10))
        notifyDataSetChanged()
    }
}