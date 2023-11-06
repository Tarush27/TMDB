package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.R
import com.example.tmdb.databinding.ScreensItemBinding
import com.example.tmdb.model.MoviesModel
import com.example.tmdb.ui.DetailsScreen
import com.example.tmdb.utils.MoviesUtils

class MoviesPagingAdapter :
    PagingDataAdapter<MoviesModel, MoviesPagingAdapter.PopularMoviesItemViewHolder>(
        MovieComparator
    ) {

    inner class PopularMoviesItemViewHolder(val binding: ScreensItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var type: String = ""

    fun setMovieType(movieType: String) {
        type = movieType
        Log.d("moviespagingadapter", "setMovieType: $type")
    }

    override fun onBindViewHolder(holder: PopularMoviesItemViewHolder, position: Int) {
        val movie = getItem(position)!!
        with(holder) {
            setIsRecyclable(false)
            with(movie) {
                binding.screensTv.text = this.popularMovieTitle
                binding.screensIv.load("${MoviesUtils.MOVIES_PAGING_SCREEN_BASE_POSTER_PATH}${this.posterPath}") {
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
                binding.screensMcv.setOnClickListener {
                    val bundle = Bundle()
                    Log.d("movpagingadapter", "movie_id: $popularMovieId")
                    bundle.putLong("movie_id", this.popularMovieId!!)
                    bundle.putString("type", type)
                    val detailsIntent =
                        Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("popmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesItemViewHolder {
        val binding =
            ScreensItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PopularMoviesItemViewHolder(binding)
    }

    object MovieComparator : DiffUtil.ItemCallback<MoviesModel>() {
        override fun areItemsTheSame(
            oldItem: MoviesModel,
            newItem: MoviesModel,
        ): Boolean {
            // Id is unique.
            return oldItem.popularMovieId == newItem.popularMovieId
        }

        override fun areContentsTheSame(
            oldItem: MoviesModel,
            newItem: MoviesModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}