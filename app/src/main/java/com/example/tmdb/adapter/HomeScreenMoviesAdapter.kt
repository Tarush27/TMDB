package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenPopularMovieSectionItemBinding
import com.example.tmdb.databinding.SingleHomeScreenMovieItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen
import java.io.File

class HomeScreenMoviesAdapter :
    RecyclerView.Adapter<HomeScreenMoviesAdapter.HomeScreenMoviesItemViewHolder>() {
    inner class HomeScreenMoviesItemViewHolder(val binding: SingleHomeScreenMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val homeScreenMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
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
                binding.homeScreenMovieIv.load("$homeScreenMoviePosterPath${this.posterPath}")
                binding.homeScreenMoviesMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movie_id", this.popularMovieId!!)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("popmovadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateHomeScreenMoviesList(updatedHomeScreenMovies: ArrayList<PopularMoviesModel>) {
        homeScreenMovies.addAll(updatedHomeScreenMovies.subList(0, 10))
        notifyDataSetChanged()
    }
}