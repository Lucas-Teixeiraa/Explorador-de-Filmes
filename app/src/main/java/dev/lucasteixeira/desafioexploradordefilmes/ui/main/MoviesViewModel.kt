package dev.lucasteixeira.desafioexploradordefilmes.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.lucasteixeira.desafioexploradordefilmes.data.model.Movie
import dev.lucasteixeira.desafioexploradordefilmes.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

enum class ListMode{
    DISCOVER,
    POPULAR
}

class MoviesViewModel : ViewModel(){
    private val repository = MovieRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResultsState = MutableStateFlow<UiState<List<Movie>>>(UiState.Success(emptyList()))
    val searchResultsState: StateFlow<UiState<List<Movie>>> = _searchResultsState

    private val _currentListMode = MutableStateFlow(ListMode.DISCOVER)
    val currentListMode: StateFlow<ListMode> = _currentListMode

    private val _listError = MutableStateFlow<String?>(null)
    val listError: StateFlow<String?> = _listError

    private val _discoverMovies = MutableStateFlow<List<Movie>>(emptyList())
    val discoverMovies: StateFlow<List<Movie>> = _discoverMovies
    private val _isDiscoverLoading = MutableStateFlow(false)
    val isDiscoverLoading: StateFlow<Boolean> = _isDiscoverLoading
    private var discoverCurrentPage = 1
    private var isDiscoverLastPage = false

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies: StateFlow<List<Movie>> = _popularMovies
    private val _isPopularLoading = MutableStateFlow(false)
    val isPopularLoading: StateFlow<Boolean> = _isPopularLoading
    private var popularCurrentPage = 1
    private var isPopularLastPage = false



    init {
        loadNextDiscoverPage()

        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .collect { query->
                if(query.isNotBlank()){
                    executeSearch(query)
                }else {
                    _searchResultsState.value = UiState.Success(emptyList())
                }
            }
        }
    }
    fun onRetryClicked(){
        if(_currentListMode.value == ListMode.DISCOVER){
            loadNextDiscoverPage()
        }else{
            loadNextPopularPage()
        }
    }

    fun loadNextPopularPage(){
        if(_isPopularLoading.value || isPopularLastPage) return

        viewModelScope.launch {
            _isPopularLoading.value = true
            if(popularCurrentPage == 1) _listError.value = null
            try {
                val newMovies = repository.getPopularMovies(page = popularCurrentPage)
                if(newMovies.isNotEmpty()){
                    val currentMovies = _popularMovies.value
                    val uniqueNewMovies = newMovies.filter{newMovie ->
                        currentMovies.none { it.id == newMovie.id}
                    }
                    if(uniqueNewMovies.isNotEmpty() || currentMovies.isEmpty() && newMovies.isNotEmpty()){
                        _popularMovies.value = currentMovies + uniqueNewMovies
                        popularCurrentPage++
                        isPopularLastPage = false
                    } else if(currentMovies.isNotEmpty() && uniqueNewMovies.isEmpty()){
                        isPopularLastPage = true
                    }
                }
                else{
                    Log.d("MoviesViewModel", "Nenhum filme popular novo e único encontrado, possivelmente última página.")
                    isPopularLastPage = true
                }
            }catch(e: Exception){
                if(popularCurrentPage == 1){
                    _listError.value = "Falha ao carregar os dados. Verifique sua conexão."
                }
                Log.e("MoviesViewModel", "Erro ao carregar proxima pagina de filmes populares", e)
            } finally {
                _isPopularLoading.value = false
            }

        }
    }

    fun loadNextDiscoverPage(){
        if(_isDiscoverLoading.value || isDiscoverLastPage){
            return
        }

        viewModelScope.launch {
            _isDiscoverLoading.value = true
            if(discoverCurrentPage == 1) _listError.value = null
            try {
                val newMovies = repository.discoverMovies(page = discoverCurrentPage)
                if(newMovies.isNotEmpty()){
                    val currentMovies = _discoverMovies.value
                    val uniqueNewMovies = newMovies.filter{newMovie ->
                        currentMovies.none { it.id == newMovie.id}
                    }
                    if(uniqueNewMovies.isNotEmpty() || currentMovies.isEmpty() && newMovies.isNotEmpty()){
                        _discoverMovies.value = currentMovies + uniqueNewMovies
                        discoverCurrentPage++
                        isDiscoverLastPage = false
                    } else if(currentMovies.isNotEmpty() && uniqueNewMovies.isEmpty()){
                        isPopularLastPage = true
                    }
                }
                else {
                    Log.d("MoviesViewModel", "API retornou lista vazia para filmes discover, marcando como última página.")
                    isDiscoverLastPage = true
                }
            }catch (e: Exception){
                if (discoverCurrentPage == 1) {
                    _listError.value = "Falha ao carregar os dados. Verifique sua conexão."
                }
                Log.e("MoviesViewModel", "Erro ao carregar próxima página", e)
            }finally {
                _isDiscoverLoading.value = false
            }

        }
    }

    fun toggleListMode(){
        if (_currentListMode.value == ListMode.DISCOVER) {
            if (_popularMovies.value.isEmpty() || isPopularLastPage) {
                popularCurrentPage = 1
                isPopularLastPage = false
                _popularMovies.value = emptyList()
                loadNextPopularPage()
            }
            _currentListMode.value = ListMode.POPULAR
        } else {
            if (_discoverMovies.value.isEmpty() || isDiscoverLastPage) {
                discoverCurrentPage = 1
                isDiscoverLastPage = false
                _discoverMovies.value = emptyList()
                loadNextDiscoverPage()
            }
            _currentListMode.value = ListMode.DISCOVER
        }
    }

    fun onSearchQueryChanged(query: String){
        _searchQuery.value = query
    }

    private fun executeSearch(query: String){
        Log.d("MoviesViewModel", "Iniciando busca por: '$query'")
        _searchResultsState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val results = repository.searchMovies(query)
                _searchResultsState.value = UiState.Success(results)
            }catch (e: Exception){
                _searchResultsState.value = UiState.Error("Falha na busca")
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
                _selectedMovie.value = UiState.Success(movieDetails)
            } catch (e: Exception) {
                _selectedMovie.value = UiState.Error("Falha ao carregar detalhes do filme.")
            }
        }
    }
}