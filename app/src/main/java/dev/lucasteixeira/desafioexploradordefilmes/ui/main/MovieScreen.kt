package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie
import dev.lucasteixeira.desafioexploradordefilmes.utils.formatDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(viewModel: MoviesViewModel, onMovieClick: (Int)->Unit) {

    val movies by viewModel.movies.observeAsState(initial = emptyList())

    Scaffold (
        topBar = {
            TopAppBar(
                title = {Text("Explorador de Filmes")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ){innerPadding ->
        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if(movies.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }else{
                MovieList(movies = movies, onMovieClick = onMovieClick)
            }
            LaunchedEffect(Unit) {
                viewModel.fetchPopularMovies()
            }
        }

    }
}

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

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier, onMovieClick: () -> Unit) {
    Card (
        modifier = modifier
            .fillMaxSize()
            .clickable{ onMovieClick()}
    ){
        Row (
           verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = "Pôster do filme ${movie.title}",
                modifier = Modifier.size(width = 100.dp, height = 150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = movie.title, style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Lançamento: ${formatDateString(movie.releaseDate)}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}