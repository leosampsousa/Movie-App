package com.hfad.movieapp.models

data class Genre(val id: Long, val name: String) {
    companion object {

        private val genreList = genreList()

        fun fromGenreId(id: Long): Genre? {
            return genreList[id]?.let { Genre(id, it) }
        }

        private fun genreList(): Map<Long, String> {
            val genres = HashMap<Long, String>()
            genres[28L] = "Ação"
            genres[12L] = "Aventura"
            genres[16L] = "Animação"
            genres[35L] = "Comédia"
            genres[80L] = "Crime"
            genres[18L] = "Drama"
            genres[99L] = "Documentário"
            genres[10751L] = "Familia"
            genres[14L] = "Fantasia"
            genres[36L] = "História"
            genres[27L] = "Terror"
            genres[10402L] = "Música"
            genres[9648L] = "Mistério"
            genres[10749L] = "Romance"
            genres[878L] = "Ficção científica"
            genres[10770L] = "Cinema TV"
            genres[53L] = "Thriller"
            genres[10752L] = "Guerra"
            genres[37L] = "Faroeste"

            return genres
        }
    }

}