package dev.lucasteixeira.desafioexploradordefilmes.data.repository

import android.annotation.SuppressLint
import dev.lucasteixeira.desafioexploradordefilmes.BuildConfig
import dev.lucasteixeira.desafioexploradordefilmes.data.api.RetrofitInstance
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie


class MovieRepository {
    @SuppressLint("newApi")
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