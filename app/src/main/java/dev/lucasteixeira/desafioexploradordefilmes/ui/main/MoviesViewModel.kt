package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie
import dev.lucasteixeira.desafioexploradordefilmes.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel(){
    private val _uiState = MutableLiveData<UiState<List<Movie>>>()
    val uiState: LiveData<UiState<List<Movie>>> = _uiState

    private val repository = MovieRepository()

    @SuppressLint("NewApi")
    fun fetchPopularMovies(){
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val popularMovies = repository.getPopularMovies()
                _uiState.postValue(UiState.Success(popularMovies))
            } catch (e: Exception) {
                _uiState.postValue(UiState.Error("Falha ao carregar os dados. Verifique sua conexão."))
            }
        }
    }

    private val _selectedMovie = MutableLiveData<UiState<Movie>>()
    val selectedMovie: LiveData<UiState<Movie>> = _selectedMovie

    fun fetchMovieById(movieId: Int) {
        _selectedMovie.value = UiState.Loading
        viewModelScope.launch {
            try {
                val movieDetails = repository.getMovieDetails(movieId)
                _selectedMovie.postValue(UiState.Success(movieDetails))
            } catch (e: Exception) {
                _selectedMovie.postValue(UiState.Error("Falha ao carregar detalhes do filme."))
            }
        }
    }
}