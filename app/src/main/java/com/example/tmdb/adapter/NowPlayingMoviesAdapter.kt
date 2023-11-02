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
//            val layoutParams = holder.itemView.layoutParams
//            if (layoutParams is MarginLayoutParams) {
//                // Calculate the new width with a 70% reduction
//                val originalWidth = layoutParams.width
//                Log.d("adap", "onBindViewHolder ow: $originalWidth")
//                val reducedWidth = (originalWidth * 0.3).toInt() // 70% reduction
//                Log.d("adap", "onBindViewHolder rw: $reducedWidth")
//                // Set the new width
//                layoutParams.width = reducedWidth
//
//                // Apply the updated layout params
//                holder.itemView.layoutParams = layoutParams
//            }

//            this.itemView.layoutParams.width =  itemView.measuredWidth - 20

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