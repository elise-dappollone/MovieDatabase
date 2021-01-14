package com.edapp.moviedatabase.models

data class Movie(val id: String, val title: String, val overview: String, val genre: Genre, val poster_path: String, val backdrop_path: String)

data class Genre(val genre_ids: List<Int>)

