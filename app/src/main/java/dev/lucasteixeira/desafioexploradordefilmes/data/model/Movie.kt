package dev.lucasteixeira.desafioexploradordefilmes.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val postPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String
)
