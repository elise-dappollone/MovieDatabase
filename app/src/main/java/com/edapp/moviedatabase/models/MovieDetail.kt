package com.edapp.moviedatabase.models

data class MovieDetail(
    val id: String,
    val revenue: Int,
    val budget: Int,
    val genres: Array<Genre>,
    val runtime: Int
)

data class Genre(val id: Int, val name: String)