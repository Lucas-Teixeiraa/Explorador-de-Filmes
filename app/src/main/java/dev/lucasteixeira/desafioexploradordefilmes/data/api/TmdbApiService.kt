package dev.lucasteixeira.desafioexploradordefilmes.data.api

import dev.lucasteixeira.desafioexploradordefilmes.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String) : MoviesResponse
}