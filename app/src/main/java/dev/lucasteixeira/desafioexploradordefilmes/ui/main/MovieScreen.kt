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
import androidx.compose.material3.Button
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
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie
import dev.lucasteixeira.desafioexploradordefilmes.utils.formatDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(viewModel: MoviesViewModel, onMovieClick: (Int)->Unit) {

    val uiState by viewModel.uiState.observeAsState()

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
            when (val state = uiState){
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Success<*> -> {
                    val movieList = state.data as? List<Movie>
                    if (movieList == null || movieList.isEmpty()){
                        Text("Nenhum filme popular encontrado")
                    }else{
                        MovieList(movies = movieList, onMovieClick = onMovieClick)
                    }
                }
                is UiState.Error ->{
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.fetchPopularMovies()
                        }) {
                            Text("Tentar Novamente")
                        }
                    }
                }
                null -> {
                    CircularProgressIndicator()
                }
            }
        }

    }
}

