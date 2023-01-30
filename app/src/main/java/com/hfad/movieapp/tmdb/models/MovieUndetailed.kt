package com.hfad.movieapp.tmdb.models

data class MovieUndetailed(
    val id: Long, val genre_ids: List<Long>, val poster_path: String,
    val title: String, val vote_average: Double )
