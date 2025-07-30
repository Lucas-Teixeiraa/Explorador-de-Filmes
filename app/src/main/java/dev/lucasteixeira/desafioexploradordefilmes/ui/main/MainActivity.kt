package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lucasteixeira.desafioexploradordefilmes.ui.details.DetailScreen
import dev.lucasteixeira.desafioexploradordefilmes.ui.theme.DesafioExploradorDeFilmesTheme

class MainActivity : ComponentActivity() {
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel.fetchPopularMovies()
        enableEdgeToEdge()
        setContent {
            DesafioExploradorDeFilmesTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "movie_list"
                    ){
                        composable("movie_list"){
                            MovieScreen(
                                viewModel = moviesViewModel,
                                onMovieClick = { movieId->
                                    navController.navigate("movie_detail/$movieId")

                                }
                            )
                        }
                        composable("movie_detail/{movieId}"){backStackEntry->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
                            if (movieId!=null){
                                DetailScreen(viewModel = moviesViewModel, movieId = movieId)
                            }

                        }
                    }
                }
            }
        }
    }
}