package com.hfad.movieapp.models

import com.hfad.movieapp.tmdb.models.Cast

data class CastModel(val name: String, val character: String, val profileUrl: String) {
    companion object {
        fun fromApiCast(apiCastResponse: Cast, baseUrl: String): CastModel {
            if(apiCastResponse.profile_path != null) {
                return CastModel(apiCastResponse.name, apiCastResponse.character, baseUrl + apiCastResponse.profile_path)
            }
            return CastModel(apiCastResponse.name, apiCastResponse.character, "")
        }
    }
}
