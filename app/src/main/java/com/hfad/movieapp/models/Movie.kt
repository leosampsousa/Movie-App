package com.hfad.movieapp.models

import com.hfad.movieapp.tmdb.models.MovieUndetailed

data class Movie(
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val genres: List<Genre>,
    val rating: Double
) {

    fun getFirstNGenres(n: Int): String {
        val sb = java.lang.StringBuilder()
        val index: Int = if (genres.size <= n) {
            genres.size
        } else {
            n;
        }
        for (i in 0 until index) {
            sb.append(genres[i].name);
            sb.append(" - ")
        }

        val response = sb.toString()
        if (response.length < 3) {
            return ""
        }
        return response.subSequence(0, response.length - 3).toString()
    }

    companion object {
        fun fromMovieUndetailed(movieUndetailed: MovieUndetailed, baseUrl: String): Movie {
            val genres: List<Genre?> =
                movieUndetailed.genre_ids.map { genreId -> Genre.fromGenreId(genreId) }

            val nonNullGenres: MutableList<Genre> = mutableListOf()
            for (genre in genres) {
                if (genre != null) {
                    nonNullGenres.add(genre)
                }
            }

            return Movie(
                movieUndetailed.id,
                movieUndetailed.title,
                baseUrl + movieUndetailed.poster_path,
                nonNullGenres,
                movieUndetailed.vote_average)
        }
    }
}