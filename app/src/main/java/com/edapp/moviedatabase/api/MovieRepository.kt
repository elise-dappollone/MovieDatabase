package com.edapp.moviedatabase.api

import com.edapp.moviedatabase.models.Movie

class MovieRepository(private val apiInterface: MovieApi) : Repository() {

    suspend fun getPopularMoviesList() : List<Movie>? {

        return makeApiCall(
            call = { apiInterface.fetchPopularMovies()},
            error = "Failed to fetch movie list"
        )?.results
    }

}