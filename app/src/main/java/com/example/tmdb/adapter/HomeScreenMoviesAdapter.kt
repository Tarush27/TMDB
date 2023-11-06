package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.databinding.SingleHomeScreenMovieItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class HomeScreenMoviesAdapter :
    RecyclerView.Adapter<HomeScreenMoviesAdapter.HomeScreenMoviesItemViewHolder>() {

    var type: String = ""

    fun setMovieType(movieType: String) {
        type = movieType
        Log.d("adapter", "setMovieType: $type")
    }

    inner class HomeScreenMoviesItemViewHolder(val binding: SingleHomeScreenMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val homeScreenMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeScreenMoviesItemViewHolder {
        val binding =
            SingleHomeScreenMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HomeScreenMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = homeScreenMovies.size

    override fun onBindViewHolder(holder: HomeScreenMoviesItemViewHolder, position: Int) {
        val homeScreenMovie = homeScreenMovies[position]
        val homeScreenMoviePosterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(homeScreenMovie) {
                binding.homeScreenMovieTv.text = this.popularMovieTitle
                binding.homeScreenMovieIv.load(
                    "$homeScreenMoviePosterPath${this.posterPath}"
                ){
//                    placeholder(R.drawable.ic_connection_error)
                    error(ContextCompat.getDrawable(itemView.context,R.drawable.ic_connection_error))
                }
                binding.homeScreenMoviesMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putLong("movie_id", this.popularMovieId!!)
                    bundle.putString("type", type)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("popmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateHomeScreenMoviesList(updatedHomeScreenMovies: ArrayList<PopularMoviesModel>) {
        homeScreenMovies.addAll(updatedHomeScreenMovies.filterIndexed { index, popularMoviesModel -> index < 10 })
        notifyDataSetChanged()
    }
}