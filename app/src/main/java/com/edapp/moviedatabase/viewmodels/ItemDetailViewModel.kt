package com.edapp.moviedatabase.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edapp.moviedatabase.api.MovieApiService
import com.edapp.moviedatabase.api.MovieRepository
import com.edapp.moviedatabase.models.MovieDetail
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ItemDetailViewModel(dispatcher: CoroutineDispatcher): ViewModel() {
    private val job = Job()
    private val coroutineContext: CoroutineContext = job + dispatcher
    private val scope = CoroutineScope(coroutineContext)

    var repository = MovieRepository(MovieApiService.movieApi)
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
        return repository.getMovieDetailsForId(movieId) ?: emptyMovieDetail
    }
}

@Suppress("UNCHECKED_CAST")
class ItemDetailViewModelFactory(private val dispatcher: CoroutineDispatcher) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) return ItemDetailViewModel(dispatcher) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}