package com.hfad.movieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.Person.fromBundle
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.imageview.ShapeableImageView
import com.hfad.movieapp.BuildConfig
import com.hfad.movieapp.MovieEndpoint
import com.hfad.movieapp.R
import com.hfad.movieapp.models.Genre
import com.hfad.movieapp.tmdb.models.MovieDetailed
import com.hfad.movieapp.util.NetworkUtils
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieScreenFragment : Fragment() {

    private val API_KEY = BuildConfig.TMDB_KEY
    private val LANGUAGE = BuildConfig.TMDB_LANGUAGE
    private val IMAGE_BASE_URI = BuildConfig.TMDB_IMAGE_BASE_URI
    private lateinit var movieImage: ShapeableImageView
    private lateinit var movieName: TextView
    private lateinit var movieYear: TextView
    private lateinit var movieRuntime: TextView
    private lateinit var movieRating: TextView
    private lateinit var chipGroup: ChipGroup
    private lateinit var movieOverview: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = MovieScreenFragmentArgs.fromBundle(requireArguments()).movieId
        getMovieDetailed(view, movieId)
    }

    private fun getMovieDetailed(view: View, movieId: Long) {
        val httpClient = NetworkUtils.getRetrofitInstance("movie/")
        val endpoint = httpClient.create(MovieEndpoint::class.java)
        val callback = endpoint.getMovieDetail(movieId, API_KEY, LANGUAGE)
        var movieDetailed: MovieDetailed

        callback.enqueue(object : Callback<MovieDetailed> {
            override fun onResponse(
                call: Call<MovieDetailed>,
                response: Response<MovieDetailed>
            ) {

                movieDetailed = response.body()!!
                addImage(view, IMAGE_BASE_URI + movieDetailed.backdrop_path)
                addMovieName(view, movieDetailed.title)
                addOverview(view, movieDetailed.overview)
                addYear(view, movieDetailed.release_date.subSequence(0,4).toString())
                addRuntime(view, movieDetailed.runtime)
                addRating(view, movieDetailed.vote_average)
                addMovieGenres(view, movieDetailed.genres)
                removeProgressBar(view)
            }

            override fun onFailure(call: Call<MovieDetailed>, t: Throwable) {
            }
        })
    }

    private fun addMovieName(view: View, name: String) {
        movieName = view.findViewById(R.id.movie_name_details)
        movieName.text = name
    }

    private fun addOverview(view: View, overview: String) {
        movieOverview = view.findViewById(R.id.movie_description_details)
        movieOverview.text = overview
    }

    private fun addYear(view: View, year: String) {
        movieYear = view.findViewById(R.id.movie_year_details)
        movieYear.text = year
    }

    private fun addRuntime(view: View, runtime: Long) {
        movieRuntime = view.findViewById(R.id.movie_runtime)
        movieRuntime.text = formatRunTime(runtime)
    }

    private fun addRating(view: View, rating: Double) {
        movieRating = view.findViewById(R.id.movie_rating_details)
        movieRating.text = rating.toString()
    }

    private fun addImage(view: View, path: String) {
        movieImage = view.findViewById(R.id.shapeableImageView)
        Picasso.get().load(path).into(movieImage)
    }

    private fun addMovieGenres(view: View, genres: List<Genre>) {
        chipGroup = view.findViewById(R.id.chip_group_details);
        val genreList = genres.map { it.name }
        for (genre in genreList) {
            val chip = layoutInflater.inflate(R.layout.unchecked_chip_layout, chipGroup, false) as Chip
            chip.text = genre
            chipGroup.addView(chip)
        }
    }

    private fun formatRunTime(timeInMinutes: Long): String {
        if (timeInMinutes < 60) {
            return timeInMinutes.toString() + "m"
        }
        var hours = 0
        var minutesLeft = timeInMinutes
        while (minutesLeft >= 60) {
            hours++
            minutesLeft -= 60
        }
        return hours.toString() + "h" + minutesLeft + "m"
    }

    private fun removeProgressBar(view: View) {
        val descriptionTitle = view.findViewById<TextView>(R.id.movie_description_title_details)
        val star = view.findViewById<ImageView>(R.id.star_details)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar_details)

        descriptionTitle.visibility = View.VISIBLE
        star.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE

    }
}