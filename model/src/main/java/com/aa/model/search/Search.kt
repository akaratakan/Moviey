package com.aa.model.search


import com.squareup.moshi.Json

data class Search(
    @Json(name = "imdbID")
    val imdbID: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Year")
    val year: String
)