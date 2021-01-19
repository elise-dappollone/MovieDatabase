package com.edapp.moviedatabase.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.Movie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemListViewModel(dispatcher: CoroutineDispatcher) : ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + dispatcher
    private val scope = CoroutineScope(coroutineContext)
    private var pageNumber = 1

    var repository = MovieRepository(MovieApiService.movieApi)
    var movieList: Array<Movie> = emptyArray()

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
class ItemListViewModelFactory(private val dispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemListViewModel::class.java)) return ItemListViewModel(dispatcher) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}