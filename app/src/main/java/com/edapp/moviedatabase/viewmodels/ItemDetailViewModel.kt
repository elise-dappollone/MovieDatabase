package com.edapp.moviedatabase.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.MovieDetail
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemDetailViewModel: ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val repository = MovieRepository(MovieApiService.movieApi)

    var movieDetail = MutableLiveData<MovieDetail>()

    private val emptyMovieDetail = MovieDetail("", 0, 0, 0)

    fun onCreate(movieId: Int) {
        val response = scope.async {
            getMovieDetails(movieId)
        }
        runBlocking {
            movieDetail.postValue(response.await())
        }
    }

    private suspend fun getMovieDetails(movieId: Int): MovieDetail {
        val result = repository.getMovieDetailsForId(movieId) ?: emptyMovieDetail
        Log.i("ItemDetailPresenter", result.toString())

        return result
    }
}

@Suppress("UNCHECKED_CAST")
class ItemDetailViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) return ItemDetailViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}