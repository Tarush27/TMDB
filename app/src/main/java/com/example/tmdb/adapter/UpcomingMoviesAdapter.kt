package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.HomeScreenUpcomingMoviesSectionItemBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.ui.DetailsScreen

class UpcomingMoviesAdapter :
    RecyclerView.Adapter<UpcomingMoviesAdapter.UpcomingMoviesItemViewHolder>() {
    inner class UpcomingMoviesItemViewHolder(val binding: HomeScreenUpcomingMoviesSectionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val upComingMovies: ArrayList<PopularMoviesModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingMoviesItemViewHolder {
        val binding =
            HomeScreenUpcomingMoviesSectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UpcomingMoviesItemViewHolder(binding)
    }

    override fun getItemCount() = upComingMovies.size

    override fun onBindViewHolder(holder: UpcomingMoviesItemViewHolder, position: Int) {
        val popularMovie = upComingMovies[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(popularMovie) {
                binding.upcomingMoviesTv.text = this.popularMovieTitle
                binding.upcomingMoviesIv.load("$posterPath${this.posterPath}")
                binding.upcomingMoviesMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("movie_id", this.popularMovieId!!)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("pma", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateUpcomingMoviesList(updatedPopularMovies: ArrayList<PopularMoviesModel>) {
        upComingMovies.addAll(updatedPopularMovies.subList(0, 10))
        notifyDataSetChanged()
    }
}