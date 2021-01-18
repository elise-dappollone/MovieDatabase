package com.edapp.moviedatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.Movie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemListViewModel : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository = MovieRepository(MovieApiService.movieApi)
    var movieList: Array<Movie> = emptyArray()
    private var pageNumber = 1

    fun onCreate() {
        val response = scope.async {
            getPopularMoviesList(pageNumber)
        }
        runBlocking {
            movieList = response.await().toTypedArray()
        }
    }

    fun loadMoreClicked() {
        pageNumber++
        val response = scope.async {
            getPopularMoviesList(pageNumber)
        }
        runBlocking {
            movieList = response.await().toTypedArray()
        }
    }

    private suspend fun getPopularMoviesList(page: Int): List<Movie> {
        return repository.getPopularMoviesList(page) ?: emptyList()
    }
}

@Suppress("UNCHECKED_CAST")
class ItemListViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) return ItemListViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}