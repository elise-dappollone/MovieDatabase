package com.edapp.moviedatabase.api

import com.edapp.moviedatabase.models.MovieListReponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {

    @GET("movie/popular")
    suspend fun fetchPopularMovies() : Response<MovieListReponse>

    @GET("movie/")
    suspend fun fetchMovieDetails(@Query("movie") movieId: String) : Response<MovieListReponse>

}