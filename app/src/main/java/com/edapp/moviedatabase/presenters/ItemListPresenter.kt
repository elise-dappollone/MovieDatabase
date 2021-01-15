package com.edapp.moviedatabase.presenters

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

    private var movieList: Array<Movie> = emptyArray()
    private var pageNumber = 1

    fun onCreate(): Array<Movie> {
        val response = scope.async {
            getPopularMoviesList(pageNumber)
        }
        runBlocking {
            movieList = response.await()?.toTypedArray()
        }
        return movieList
    }

    fun loadMoreClicked(): Array<Movie> {
        val response = scope.async {
            getPopularMoviesList(pageNumber++)
        }
        runBlocking {
            movieList = response.await()?.toTypedArray()
        }
        return movieList
    }

    suspend fun getPopularMoviesList(page: Int): List<Movie> {
        return repository.getPopularMoviesList(page) ?: emptyList()
    }
}