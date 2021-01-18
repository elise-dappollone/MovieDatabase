package com.edapp.moviedatabase.presenters

import android.util.Log
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.MovieDetail
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemDetailPresenter {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository = MovieRepository(MovieApiService.movieApi)

    private lateinit var movieDetail: MovieDetail

    private val emptyMovieDetail = MovieDetail("", 0, 0, emptyArray(), 0)

    fun onCreate(movieId: Int): MovieDetail {
        val response = scope.async {
            getMovieDetails(movieId)
        }
        runBlocking {
            movieDetail = response.await()
        }
        return movieDetail
    }

    private suspend fun getMovieDetails(movieId: Int): MovieDetail {
        val result = repository.getMovieDetailsForId(movieId) ?: emptyMovieDetail
        Log.i("ItemDetailPresenter", result.toString())

        return result
    }
}