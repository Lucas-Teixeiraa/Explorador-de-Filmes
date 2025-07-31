package dev.lucasteixeira.desafioexploradordefilmes.data.api

import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie
import dev.lucasteixeira.desafioexploradordefilmes.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String) : MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie
}