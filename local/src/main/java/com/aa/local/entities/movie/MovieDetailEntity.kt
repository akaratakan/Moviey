package com.aa.local.entities.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String?,
    val rated: String?,
    val released: String?,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String,
    val language: String?,
    val country: String?,
    val awards: String?,
    val poster: String,
    val metascore: String?,
    val imdbRating: String?,
    val imdbVotes: String?,
    val type: String?,
    val dVD: String?,
    val boxOffice: String?,
    val production: String?,
    val website: String?,
    val response: String?,
    var isFav: Boolean = false
)