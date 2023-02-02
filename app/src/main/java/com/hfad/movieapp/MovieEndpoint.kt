package com.hfad.movieapp

import com.hfad.movieapp.tmdb.models.MovieCast
import com.hfad.movieapp.tmdb.models.MovieDetailed
import com.hfad.movieapp.tmdb.models.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieEndpoint {
    @GET("popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListResponse>

    @GET("now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListResponse>

    @GET("top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieListResponse>

    @GET("{movieId}")
    fun getMovieDetail(
        @Path("movieId") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Call<MovieDetailed>

    @GET("{movieId}/credits")
    fun getMovieCast(
        @Path("movieId") movieId: Long,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Call<MovieCast>
}