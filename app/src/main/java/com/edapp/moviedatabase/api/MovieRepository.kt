package com.edapp.moviedatabase.api

import com.edapp.moviedatabase.models.Movie

class MovieRepository(private val movieApi: MovieApi) : Repository() {

    suspend fun getPopularMoviesList(page: Int) : List<Movie>? {

        return makeApiCall(
            call = { movieApi.fetchPopularMovies(page)},
            error = "Failed to fetch movie list"
        )?.results
    }
}