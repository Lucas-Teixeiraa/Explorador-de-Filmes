package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie

@Composable
fun MovieList(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyColumn (
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ){
        items(movies){movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier.padding(vertical = 8.dp),
                onMovieClick = {
                    onMovieClick(movie.id)
                }
            )
        }
    }
}