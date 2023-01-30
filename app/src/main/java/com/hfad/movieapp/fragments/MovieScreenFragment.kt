package com.hfad.movieapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.hfad.movieapp.R
import com.hfad.movieapp.adapters.MoviePreviewAdapter
import com.hfad.movieapp.adapters.TopicAdapter


class MovieScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMovieGenres(view)
    }

    private fun addMovieGenres(view: View) {
        val chipGroup = view.findViewById<ChipGroup>(R.id.chip_group_details);
        val genreList =
            listOf(
                "Ação",
                "Aventura",
                "Comédia"
            )
        for (genre in genreList) {
            val chip = layoutInflater.inflate(R.layout.unchecked_chip_layout, chipGroup, false) as Chip
            chip.text = genre
            chipGroup.addView(chip)
        }
    }


}