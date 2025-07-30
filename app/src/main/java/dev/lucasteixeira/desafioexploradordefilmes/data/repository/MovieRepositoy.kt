package dev.lucasteixeira.desafioexploradordefilmes.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import dev.lucasteixeira.desafioexploradordefilmes.BuildConfig
import dev.lucasteixeira.desafioexploradordefilmes.data.api.RetrofitInstance
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie


class MovieRepositoy {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun getPopularMovies(): List<Movie>{
        return try {
            val apiKey = BuildConfig.API_KEY
            val response = RetrofitInstance.api.getPopularMovies(apiKey = apiKey)
            response.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}