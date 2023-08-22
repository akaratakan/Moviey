package com.aa.model.search


import com.squareup.moshi.Json


data class SearchResponse(
    @Json(name = "Response")
    val response: String,
    @Json(name = "Search")
    val search: List<Search>,
    @Json(name = "totalResults")
    val totalResults: String
)