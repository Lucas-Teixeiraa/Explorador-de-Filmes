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
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies
    private val repositoy = MovieRepository()

    @SuppressLint("NewApi")
    fun fetchPopularMovies(){
        viewModelScope.launch {
            val popularMovies = repositoy.getPopularMovies()
            _movies.postValue(popularMovies)
        }
    }
}