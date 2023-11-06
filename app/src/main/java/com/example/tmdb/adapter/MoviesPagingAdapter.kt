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
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.ScreensItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class MoviesPagingAdapter :
    PagingDataAdapter<PopularMoviesModel, MoviesPagingAdapter.PopularMoviesItemViewHolder>(
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
        val posterPath = "https://image.tmdb.org/t/p/original"
        with(holder) {
            setIsRecyclable(false)
            with(movie) {
                binding.screensTv.text = this.popularMovieTitle
//                Glide.with(itemView.context).load("$posterPath${this.posterPath}").apply {
//                }.into(binding.screensIv)

                binding.screensIv.load("$posterPath${this.posterPath}") {
                    error(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_connection_error
                        )
                    )
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

    object MovieComparator : DiffUtil.ItemCallback<PopularMoviesModel>() {
        override fun areItemsTheSame(
            oldItem: PopularMoviesModel,
            newItem: PopularMoviesModel,
        ): Boolean {
            // Id is unique.
            return oldItem.popularMovieId == newItem.popularMovieId
        }

        override fun areContentsTheSame(
            oldItem: PopularMoviesModel,
            newItem: PopularMoviesModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}