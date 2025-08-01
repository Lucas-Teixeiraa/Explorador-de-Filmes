package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie

@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    isLoading: Boolean = false,
    onLoadNextPage: (() -> Unit)? = null

) {
    val listState = rememberLazyListState()

    LazyColumn (
        modifier = modifier,
        state = listState
    ){
        items(
            movies,
            key = {movie -> movie.id}
        ){movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier.padding(vertical = 8.dp),
                onMovieClick = {
                    onMovieClick(movie.id)
                }
            )
        }
        if(isLoading){
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
        }
    }

    if (onLoadNextPage != null) {
        LaunchedEffect(listState) {
            snapshotFlow {
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            }.collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= movies.size - 5 && !isLoading) {
                    onLoadNextPage()
                }
            }
        }
    }
}