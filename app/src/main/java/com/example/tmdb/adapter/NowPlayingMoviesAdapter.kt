package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.NowPlayingMoviesSectionItemBinding
import com.example.tmdb.model.NowPlayingMoviesModel

class NowPlayingMoviesAdapter :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.NowPlayingMoviesItemViewHolder>() {
    inner class NowPlayingMoviesItemViewHolder(val binding: NowPlayingMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val nowPlayingMovies: ArrayList<NowPlayingMoviesModel> = arrayListOf()
    private var width = 0 // declared height globally in Adapter class to reuse it.


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NowPlayingMoviesItemViewHolder {
        val binding = NowPlayingMoviesSectionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
//        binding.root.layoutParams = ViewGroup.LayoutParams(
//            (parent.width * 0.7).toInt(),
//            500
//        )

//        binding.root.layoutParams.width = (parent.width * 0.8).toInt()

        if (width == 0) width = (parent.width * 0.8).toInt()
////        binding.root.minWidth = width
        binding.root.layoutParams.width = width
        return NowPlayingMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = nowPlayingMovies.size

    override fun onBindViewHolder(holder: NowPlayingMoviesItemViewHolder, position: Int) {
        val nowPlayingMovie = nowPlayingMovies[position]
        val posterPath = "https://image.tmdb.org/t/p/w342"
        val backDropPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(nowPlayingMovie) {
                binding.nowPlayingMovTitle.text = this.nowPlayingMovieTitle
                binding.nowPlayingMovPosterIv.load("$posterPath${this.posterPath}") {
                    error(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_connection_error_now_playing
                        )
                    )
                }
                binding.movieBackdropIv.load("$backDropPath${this.backdrop_path}") {
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