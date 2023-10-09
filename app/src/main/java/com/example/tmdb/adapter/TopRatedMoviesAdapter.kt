package com.example.tmdb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenTopRatedMoviesSectionItemBinding
import com.example.tmdb.model.PopularMoviesModel

class TopRatedMoviesAdapter :
    RecyclerView.Adapter<TopRatedMoviesAdapter.TopRatedMoviesItemViewHolder>() {
    inner class TopRatedMoviesItemViewHolder(val binding: HomeScreenTopRatedMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val topRatedMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMoviesItemViewHolder {
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
                Log.d("adapter", "onBindViewHolder:$posterPath${this.posterPath} ")
            }
        }

    }

    fun updateTopRatedMoviesList(updatedPopularMovies: ArrayList<PopularMoviesModel>) {
        topRatedMovies.addAll(updatedPopularMovies.subList(0,10))
        notifyDataSetChanged()
    }
}