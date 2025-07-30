package dev.lucasteixeira.desafioexploradordefilmes.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory
            .create()).build()
    }

    val api: TmdbApiService by lazy { retrofit.create(TmdbApiService::class.java) }
}