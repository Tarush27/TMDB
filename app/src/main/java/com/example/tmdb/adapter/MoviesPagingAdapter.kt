package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.ScreensItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class MoviesPagingAdapter :
    PagingDataAdapter<PopularMoviesModel, MoviesPagingAdapter.PopularMoviesItemViewHolder>(
        MovieComparator
    ) {

    inner class PopularMoviesItemViewHolder(val binding: ScreensItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: PopularMoviesItemViewHolder, position: Int) {
        val movie = getItem(position)!!
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            setIsRecyclable(false)
            with(movie) {
                binding.screensTv.text = this.popularMovieTitle
                binding.screensIv.load("$posterPath${this.posterPath}")
                binding.screensMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movie_id", this.popularMovieId!!)
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
            newItem: PopularMoviesModel
        ): Boolean {
            // Id is unique.
            return oldItem.popularMovieId == newItem.popularMovieId
        }

        override fun areContentsTheSame(
            oldItem: PopularMoviesModel,
            newItem: PopularMoviesModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}