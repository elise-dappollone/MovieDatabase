package com.edapp.moviedatabase.models

import java.io.Serializable


data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String) : Serializable

data class Genre(val genre_ids: List<Int>)

