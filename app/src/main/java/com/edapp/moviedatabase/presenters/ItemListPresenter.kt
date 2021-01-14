package com.edapp.moviedatabase.presenters

import android.util.Log
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.Movie
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemListPresenter {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository = MovieRepository(MovieApiService.movieApi)


    fun onCreate(): List<Movie> {
        var movieList: List<Movie> = emptyList()
        val response = scope.async {
            getPopularMoviesList()
        }
        runBlocking {
            movieList = response.await()?.toList()
            Log.i("ItemListPresenter", response.toString())
        }
        return movieList
    }

    suspend fun getPopularMoviesList(): List<Movie> {
        return repository.getPopularMoviesList() ?: emptyList()
    }
}