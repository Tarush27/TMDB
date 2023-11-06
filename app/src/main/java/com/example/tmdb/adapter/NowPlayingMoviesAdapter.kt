package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.NowPlayingMoviesSectionItemBinding
import com.example.tmdb.model.NowPlayingMoviesModel
import com.example.tmdb.utils.MoviesUtils

class NowPlayingMoviesAdapter :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.NowPlayingMoviesItemViewHolder>() {
    inner class NowPlayingMoviesItemViewHolder(val binding: NowPlayingMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val nowPlayingMovies: ArrayList<NowPlayingMoviesModel> = arrayListOf()
    private var width = 0


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NowPlayingMoviesItemViewHolder {
        val binding = NowPlayingMoviesSectionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        if (width == 0) width = (parent.width * 0.8).toInt()
        binding.root.layoutParams.width = width
        return NowPlayingMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = nowPlayingMovies.size

    override fun onBindViewHolder(holder: NowPlayingMoviesItemViewHolder, position: Int) {
        val nowPlayingMovie = nowPlayingMovies[position]
        with(holder) {
            with(nowPlayingMovie) {
                binding.nowPlayingMovTitle.text = this.nowPlayingMovieTitle
                binding.nowPlayingMovPosterIv.load("${MoviesUtils.NOW_PLAYING_MOVIES_BASE_POSTER_PATH}${this.posterPath}") {
                    try {
                        error(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_connection_error_now_playing
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
//                    error(
//                        ContextCompat.getDrawable(
//                            itemView.context,
//                            R.drawable.ic_connection_error_now_playing
//                        )
//                    )
                }
                binding.movieBackdropIv.load("${MoviesUtils.NOW_PLAYING_MOVIES_BASE_BACKDROP_PATH}${this.backdrop_path}") {
                    error(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_connection_error_now_playing_mcv
                        )
                    )
                }
                binding.nowPlayingMovOverview.text = this.nowPlayingMovieOverview!!.trim()
            }
        }

    }

    fun updateNowPlayingMoviesList(updatedNowPlayingMovies: ArrayList<NowPlayingMoviesModel>) {
        nowPlayingMovies.addAll(updatedNowPlayingMovies)
        notifyDataSetChanged()
    }
}