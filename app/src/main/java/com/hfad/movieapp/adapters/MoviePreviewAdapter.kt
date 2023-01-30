package com.hfad.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hfad.movieapp.R
import com.hfad.movieapp.models.Movie
import com.hfad.movieapp.models.Topic
import com.squareup.picasso.Picasso

class MoviePreviewAdapter(var movies: List<Movie>) : RecyclerView.Adapter<MoviePreviewAdapter.MoviePreviewViewHolder>(){

    var onItemClick: ((Topic) -> Unit)? = null

    inner class MoviePreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImageView: ImageView = itemView.findViewById(R.id.movie_image)
        val movieNameView: TextView = itemView.findViewById(R.id.movie_name_details)
        val movieRatingView: TextView = itemView.findViewById(R.id.movie_rating_details)
        val movieGenresView: TextView = itemView.findViewById(R.id.movie_genres)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePreviewViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MoviePreviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviePreviewViewHolder, position: Int) {
        val currentMovie = movies[position]
        val movieImageView = holder.movieImageView
        val movieNameView = holder.movieNameView
        val movieRatingView = holder.movieRatingView
        val movieGenresView = holder.movieGenresView

        Picasso.get().load(currentMovie.posterUrl).into(movieImageView)
        movieNameView.text = currentMovie.title
        movieRatingView.text = currentMovie.rating.toString()
        movieGenresView.text = currentMovie.getFirstNGenres(3)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}