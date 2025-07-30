package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie

class MoviesViewModel : ViewModel(){
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies
    
}