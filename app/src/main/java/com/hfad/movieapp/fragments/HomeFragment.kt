package com.hfad.movieapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.hfad.movieapp.BuildConfig
import com.hfad.movieapp.R
import com.hfad.movieapp.adapters.MoviePreviewAdapter
import com.hfad.movieapp.adapters.TopicAdapter
import com.hfad.movieapp.models.Movie
import com.hfad.movieapp.tmdb.models.MovieEndpoint
import com.hfad.movieapp.tmdb.models.MovieListResponse
import com.hfad.movieapp.util.NetworkUtils
import com.hfad.movieapp.util.RandomUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val API_KEY = BuildConfig.TMDB_KEY
    private val LANGUAGE = BuildConfig.TMDB_LANGUAGE
    private val IMAGE_BASE_URI = BuildConfig.TMDB_IMAGE_BASE_URI
    private lateinit var topicListView: RecyclerView
    private lateinit var movieListView: RecyclerView
    private lateinit var chipGroupView: ChipGroup
    private var popularMovies = listOf<Movie>()
    private var nowPlayingMovies = listOf<Movie>()
    private var allTimeBestMovies = listOf<Movie>()
    private var currentMovieList = popularMovies;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topicListView = view.findViewById(R.id.topic_list)
        topicListView.layoutManager = layoutManager

        val adapter = TopicAdapter()
        topicListView.adapter = adapter
        addMovieGenres(view)

        val movieListLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        movieListView = view.findViewById(R.id.movie_list)
        movieListView.layoutManager = movieListLayoutManager


        val movieListAdapter = MoviePreviewAdapter(currentMovieList)
        movieListView.adapter = movieListAdapter


        chipGroupView = view.findViewById(R.id.chip_group)
        chipGroupView.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) {
                movieListAdapter.movies = currentMovieList
            } else {
                val chip = group.findViewById<Chip>(checkedIds[0])
                movieListAdapter.movies = currentMovieList.filter { movie ->
                    movie.genres.map { genre -> genre.name }.contains(chip.text)
                }
            }
            movieListAdapter.notifyDataSetChanged()
        }

        adapter.onItemClick = {
            adapter.cleanNavGuide()
            adapter.updateNavGuideSelectedItem(it)
            currentMovieList = when (it.name) {
                "Populares" -> popularMovies
                "Em cartaz" -> nowPlayingMovies
                else -> allTimeBestMovies
            }
            movieListAdapter.movies = currentMovieList
            chipGroupView.clearCheck()
            movieListAdapter.notifyDataSetChanged()
        }

        getPopularMovies(movieListAdapter)
        getNowPlayingMovies()
        getAllTimeBestMovies()
    }

    private fun addMovieGenres(view: View) {
        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group_details);
        val genreList =
            listOf(
                "Ação",
                "Aventura",
                "Comédia",
                "Crime",
                "Drama",
                "Mistério",
                "Romance",
                "Ficção científica",
                "Terror"
            )
        for (genre in genreList) {
            val chip = layoutInflater.inflate(R.layout.chip_layout, chipGroup, false) as Chip
            chip.text = genre
            chipGroup.addView(chip)
        }
    }

    private fun getPopularMovies(moviePreviewAdapter: MoviePreviewAdapter) {
        val httpClient = NetworkUtils.getRetrofitInstance("movie/")
        val endpoint = httpClient.create(MovieEndpoint::class.java)
        val callback = endpoint.getPopularMovies(API_KEY, LANGUAGE, 1)
        val movieList = mutableListOf<Movie>()
        var movieListResponse: MovieListResponse

        callback.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                movieListResponse = response.body()!!
                for (movie in movieListResponse.results) {
                    movieList.add(
                        Movie.fromMovieUndetailed(
                            movie,
                            IMAGE_BASE_URI
                        )
                    )
                }

                popularMovies = movieList.toMutableList().shuffled()
                currentMovieList = popularMovies
                moviePreviewAdapter.movies = currentMovieList
                moviePreviewAdapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                currentMovieList = movieList
                moviePreviewAdapter.movies = currentMovieList
                moviePreviewAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun getNowPlayingMovies() {
        val httpClient = NetworkUtils.getRetrofitInstance("movie/")
        val endpoint = httpClient.create(MovieEndpoint::class.java)
        val callback = endpoint.getNowPlaying(API_KEY, LANGUAGE, RandomUtil.rand(1,3))
        val movieList = mutableListOf<Movie>()
        var movieListResponse: MovieListResponse

        callback.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                movieListResponse = response.body()!!
                for (movie in movieListResponse.results) {
                    movieList.add(
                        Movie.fromMovieUndetailed(
                            movie,
                            IMAGE_BASE_URI
                        )
                    )
                }

                nowPlayingMovies = movieList.toMutableList().shuffled()
            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                nowPlayingMovies = movieList
            }
        })
    }

    private fun getAllTimeBestMovies() {
        val httpClient = NetworkUtils.getRetrofitInstance("movie/")
        val endpoint = httpClient.create(MovieEndpoint::class.java)
        val callback = endpoint.getTopRated(API_KEY, LANGUAGE, RandomUtil.rand(1,3))
        val movieList = mutableListOf<Movie>()
        var movieListResponse: MovieListResponse

        callback.enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                movieListResponse = response.body()!!
                for (movie in movieListResponse.results) {
                    movieList.add(
                        Movie.fromMovieUndetailed(
                            movie,
                            IMAGE_BASE_URI
                        )
                    )
                }

                allTimeBestMovies = movieList.toMutableList().shuffled()

            }

            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                allTimeBestMovies = movieList
            }
        })
    }
}