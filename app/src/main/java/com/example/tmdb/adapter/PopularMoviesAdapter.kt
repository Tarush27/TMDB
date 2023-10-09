package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenPopularMovieSectionItemBinding
import com.example.tmdb.model.PopularMoviesModel

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

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: PopularMoviesItemViewHolder, position: Int) {
        val popularMovie = popularMovies[position]
        with(holder) {
            with(popularMovie) {
                binding.popularMovieTv.text = this.popularMovieTitle
                binding.popularMovieIv.load(this.posterPath)
            }
        }

    }
}