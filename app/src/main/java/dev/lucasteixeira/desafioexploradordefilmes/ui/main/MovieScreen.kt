package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(viewModel: MoviesViewModel, onMovieClick: (Int)->Unit) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResultsState by viewModel.searchResultsState.collectAsState()
    val listMode by viewModel.currentListMode.collectAsState()

    val discoverMovies by viewModel.discoverMovies.collectAsState()
    val isDiscoverLoading by viewModel.isDiscoverLoading.collectAsState()

    val popularMovies by viewModel.popularMovies.collectAsState()
    val isPopularLoading by viewModel.isPopularLoading.collectAsState()

    val listError by viewModel.listError.collectAsState()

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
        Column (modifier = Modifier.padding(innerPadding).fillMaxSize()){
            SearchBarMovies(
                query = searchQuery,
                onQueryChanged = viewModel::onSearchQueryChanged
            )
            Button(
                onClick = { viewModel.toggleListMode()},
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                val buttonText = when(listMode){
                    ListMode.DISCOVER -> "Ver Filmes Populares"
                    ListMode.POPULAR -> "Ver todos os Filmes"
                }
                Text(buttonText)
            }

            if(searchQuery.isNotBlank()){
                SearchResultContent(state = searchResultsState, onMovieClick = onMovieClick)
            } else {
                val isDiscoverListEmpty = discoverMovies.isEmpty()
                val isPopularListEmpty = popularMovies.isEmpty()

                if (listError != null && ((listMode == ListMode.DISCOVER && isDiscoverListEmpty) || (listMode == ListMode.POPULAR && isPopularListEmpty))) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        ErrorView(message = listError!!, onRetry = viewModel::onRetryClicked)
                    }
                } else {
                    when (listMode) {
                        ListMode.DISCOVER -> {
                            if (isDiscoverLoading && isDiscoverListEmpty) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                MovieList(
                                    modifier = Modifier.weight(1f),
                                    movies = discoverMovies,
                                    isLoading = isDiscoverLoading,
                                    onLoadNextPage = viewModel::loadNextDiscoverPage,
                                    onMovieClick = onMovieClick
                                )
                            }
                        }
                        ListMode.POPULAR -> {
                            if (isPopularLoading && isPopularListEmpty) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                MovieList(
                                    modifier = Modifier.weight(1f),
                                    movies = popularMovies,
                                    isLoading = isPopularLoading,
                                    onLoadNextPage = viewModel::loadNextPopularPage,
                                    onMovieClick = onMovieClick
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}



@Composable
fun SearchResultContent(state: UiState<List<Movie>>, onMovieClick: (Int) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(state){
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success->{
                if (state.data.isEmpty()){
                    Text("Nenhum resultado encontrado para sua busca")
                } else{
                    MovieList(movies = state.data, onMovieClick = onMovieClick)
                }
            }
            is UiState.Error ->{
                ErrorView(message = state.message, onRetry = {})
            }
            null ->{}
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry){
            Text("Tentar Novamente")
        }
    }
}

@Composable
fun SearchBarMovies(query: String, onQueryChanged: (String) -> Unit){
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = {Text("Pesquisar por filmes...")},
        singleLine =  true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )

}

