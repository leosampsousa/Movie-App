package com.hfad.movieapp.tmdb.models

import com.hfad.movieapp.models.Genre

data class MovieDetailed(
    val id: Long,
    val backdrop_path: String,
    val genres: List<Genre>,
    val overview: String,
    val release_date: String,
    val runtime: Long,
    val title: String,
    val vote_average: Double
)
