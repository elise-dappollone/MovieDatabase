package com.edapp.moviedatabase.api

import com.edapp.moviedatabase.models.Movie
import com.edapp.moviedatabase.models.MovieDetail

class MovieRepository(private val movieApi: MovieApi) : Repository() {

    suspend fun getPopularMoviesList(page: Int) : List<Movie>? {

        return makeApiCall(
            call = { movieApi.fetchPopularMovies(page)},
            error = "Failed to fetch movie list"
        )?.results
    }

    suspend fun getMovieDetailsForId(movieId: Int) : MovieDetail? {
        return makeApiCall(
            call = { movieApi.fetchMovieDetails(movieId)},
            error = "Failed to fetch movie list"
        )
    }
}