package com.aa.data

import com.aa.model.search.SearchResponse
import com.aa.network.api.MovieApi
import javax.inject.Inject

/**
 * Created by atakanakar
 */

class MovieRepository @Inject constructor(private val api: MovieApi) {

    suspend fun fetchMovie(name:String): SearchResponse {
        return api.searchMovie(name)
    }
}