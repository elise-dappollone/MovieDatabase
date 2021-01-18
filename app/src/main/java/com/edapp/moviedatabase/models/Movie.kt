package com.edapp.moviedatabase.models

import java.io.Serializable


data class Movie(
    val id: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val revenue: Int,
    val budget: Int,
    val runtime: Int) : Serializable



