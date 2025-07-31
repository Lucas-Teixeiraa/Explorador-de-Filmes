package dev.lucasteixeira.desafioexploradordefilmes.data.repository

import android.annotation.SuppressLint
import dev.lucasteixeira.desafioexploradordefilmes.BuildConfig
import dev.lucasteixeira.desafioexploradordefilmes.data.api.RetrofitInstance
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie


class MovieRepository {
    @SuppressLint("newApi")
    suspend fun getPopularMovies(page: Int): List<Movie>{
        val apiKey = BuildConfig.API_KEY
        val response = RetrofitInstance.api.getPopularMovies(apiKey = apiKey, page = page)
        return response.results
    }
    suspend fun getMovieDetails(movieId: Int): Movie {
        val apiKey = BuildConfig.API_KEY
        return RetrofitInstance.api.getMovieDetails(movieId = movieId, apiKey = apiKey)
    }

    suspend fun searchMovies(query: String): List<Movie>{
        val apiKey = BuildConfig.API_KEY
        val response = RetrofitInstance.api.searchMovies(apiKey = apiKey, query = query)
        return response.results
    }

    suspend fun discoverMovies(page:Int): List<Movie>{
        val apiKey = BuildConfig.API_KEY
        val response = RetrofitInstance.api.discoverMovies(apiKey = apiKey, page = page)
        return response.results
    }
}