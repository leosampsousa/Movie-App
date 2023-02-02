package com.hfad.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.hfad.movieapp.R
import com.hfad.movieapp.models.CastModel
import com.squareup.picasso.Picasso

class CastAdapter(var cast: List<CastModel>): RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val castImage: ShapeableImageView = itemView.findViewById(R.id.profile_pic)
        val castName: TextView = itemView.findViewById(R.id.cast_name)
        val castCharacter: TextView = itemView.findViewById(R.id.cast_character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)

        return CastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val currentPerson = cast[position]
        val castImage = holder.castImage
        val castName = holder.castName
        val castCharacter = holder.castCharacter

        Picasso.get().load(currentPerson.profileUrl).into(castImage)
        castName.text = currentPerson.name
        castCharacter.text = currentPerson.character
    }

    override fun getItemCount(): Int {
        return cast.size
    }
}