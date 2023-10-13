package com.example.tmdb.adapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tmdb.databinding.SingleTrendingTvShowsDayWeekBinding
import com.example.tmdb.model.PopularMoviesModel
import com.example.tmdb.model.TrendingTVShowsModel
import com.example.tmdb.ui.DetailsScreen

class TrendingTVShowsAdapter :
    RecyclerView.Adapter<TrendingTVShowsAdapter.TrendingTVShowsItemViewHolder>() {
    inner class TrendingTVShowsItemViewHolder(val binding: SingleTrendingTvShowsDayWeekBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val trendingTVShows: ArrayList<TrendingTVShowsModel> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingTVShowsItemViewHolder {
        val binding =
            SingleTrendingTvShowsDayWeekBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrendingTVShowsItemViewHolder(binding)
    }

    override fun getItemCount() = trendingTVShows.size

    override fun onBindViewHolder(holder: TrendingTVShowsItemViewHolder, position: Int) {
        val popularMovie = trendingTVShows[position]
        val posterPath = "https://image.tmdb.org/t/p/w500"
        with(holder) {
            with(popularMovie) {
                binding.trendingTVShowsIv.load("$posterPath${this.trendingTVShowPosterPath}")
                binding.trendingTVShowsTv.text = this.trendingTVShowName
                binding.trendingTVShowsMcv.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("trending_tv_show_id", this.trendingTVShowId!!)
                    val detailsIntent = Intent(it.context, DetailsScreen::class.java)
                    detailsIntent.putExtras(bundle)
                    Log.d("trendingtvshowsadapter", "detailsIntent: $detailsIntent")
                    it.context.startActivity(detailsIntent)
                }
            }
        }

    }

    fun updateTrendingTVShowsList(updatedTrendingTVShows: ArrayList<TrendingTVShowsModel>) {
        trendingTVShows.clear()
        trendingTVShows.addAll(updatedTrendingTVShows)
        notifyDataSetChanged()
    }
}