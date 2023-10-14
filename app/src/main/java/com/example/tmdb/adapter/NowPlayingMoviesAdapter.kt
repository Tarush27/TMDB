package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.NowPlayingMoviesSectionItemBinding
import com.example.tmdb.model.NowPlayingMoviesModel

class NowPlayingMoviesAdapter :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.NowPlayingMoviesItemViewHolder>() {
    inner class NowPlayingMoviesItemViewHolder(val binding: NowPlayingMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val nowPlayingMovies: ArrayList<NowPlayingMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingMoviesItemViewHolder {
        val binding =
            NowPlayingMoviesSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NowPlayingMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = nowPlayingMovies.size

    override fun onBindViewHolder(holder: NowPlayingMoviesItemViewHolder, position: Int) {
        val nowPlayingMovie = nowPlayingMovies[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        val backDropPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(nowPlayingMovie) {
                binding.nowPlayingMovTitle.text = this.nowPlayingMovieTitle
                binding.nowPlayingMovPosterIv.load("$posterPath${this.posterPath}")
                binding.movieBackdropIv.load("$backDropPath${this.backdrop_path}")
                binding.nowPlayingMovOverview.text = this.nowPlayingMovieOverview!!.trim()
            }
        }

    }

    fun updateNowPlayingMoviesList(updatedNowPlayingMovies: ArrayList<NowPlayingMoviesModel>) {
        nowPlayingMovies.addAll(updatedNowPlayingMovies)
        notifyDataSetChanged()
    }
}