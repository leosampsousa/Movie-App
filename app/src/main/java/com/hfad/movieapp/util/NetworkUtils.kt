package com.hfad.movieapp.util

import com.hfad.movieapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    companion object {

        private val API_BASE_URI = BuildConfig.TMDB_BASE_URI
        private val API_VERSION = BuildConfig.TMDB_VERSION

        fun getRetrofitInstance(path: String): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl("${API_BASE_URI}/${API_VERSION}/${path}/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}