package dev.lucasteixeira.desafioexploradordefilmes.data.repository

import android.annotation.SuppressLint
import dev.lucasteixeira.desafioexploradordefilmes.BuildConfig
import dev.lucasteixeira.desafioexploradordefilmes.data.api.RetrofitInstance
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie


class MovieRepository {
    @SuppressLint("newApi")
    suspend fun getPopularMovies(): List<Movie>{
        val apiKey = BuildConfig.API_KEY
        val response = RetrofitInstance.api.getPopularMovies(apiKey = apiKey)
        return response.results
    }
    suspend fun getMovieDetails(movieId: Int): Movie {
        val apiKey = BuildConfig.API_KEY
        return RetrofitInstance.api.getMovieDetails(movieId = movieId, apiKey = apiKey)
    }
}