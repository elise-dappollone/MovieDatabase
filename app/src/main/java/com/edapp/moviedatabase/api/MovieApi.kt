package com.edapp.moviedatabase.api

import com.edapp.moviedatabase.models.MovieDetail
import com.edapp.moviedatabase.models.MovieListReponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("movie/popular?")
    suspend fun fetchPopularMovies(@Query("page") page: Int) : Response<MovieListReponse>

    @GET("movie/{movieId}")
    suspend fun fetchMovieDetails(@Path("movieId") movieId: Int) : Response<MovieDetail>
}