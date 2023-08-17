package com.aa.network.api

import com.aa.model.movie.DetailResponseObject
import com.aa.model.search.SearchResponse
import com.aa.network.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET(".")
    suspend fun searchMovie(
        @Query("s") keyword: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
    ): SearchResponse

    @GET(".")
    suspend fun getMovieById(
        @Query("i") imdbId: String?,
        @Query("plot") plot: String?="full",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY,
    ): DetailResponseObject

}