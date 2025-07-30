package dev.lucasteixeira.desafioexploradordefilmes.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.lucasteixeira.desafioexploradordefilmes.ui.main.MoviesViewModel
import dev.lucasteixeira.desafioexploradordefilmes.utils.formatDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: MoviesViewModel, movieId: Int, onNavigateUp: () -> Unit) {
    val movies by viewModel.movies.observeAsState(initial = emptyList())
    val movie = movies.find{it.id == movieId}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie?.title ?: "Detalhes") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            if(movie !=null){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ){
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                        contentDescription = "Pôster do filme ${movie.title}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = movie.title, style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Lançamento: ${formatDateString(movie.releaseDate)}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Descrição: ", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = movie.overview, style = MaterialTheme.typography.bodyLarge)
                }
            } else{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Filme nao encontrato")
                }
            }
        }
    }


}