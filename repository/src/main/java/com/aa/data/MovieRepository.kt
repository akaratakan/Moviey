package com.aa.data

import com.aa.local.entities.movie.MovieDao
import com.aa.local.entities.movie.MovieDetailEntity
import com.aa.model.movie.MovieDetailResponse
import com.aa.model.search.SearchResponse
import com.aa.network.api.MovieApi
import javax.inject.Inject

/**
 * Created by atakanakar
 */

class MovieRepository @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao
) {

    suspend fun searchMovie(name: String): SearchResponse {
        return api.searchMovie(name)
    }

    suspend fun fetchMovieWithId(id: String): MovieDetailResponse {
        return api.getMovieById(id)
    }

    fun insertDetail(detailEntity: MovieDetailEntity){
        movieDao.insertMovie(detailEntity)
    }

    fun fetchDetailWithId(imdbId: String): MovieDetailEntity? {
       return movieDao.fetchMovieWithId(imdbId)
    }

    fun fetchAll(): List<MovieDetailEntity>? {
       return movieDao.fetchAll()
    }

    fun deleteDetail(detailEntity: MovieDetailEntity) {
        movieDao.deleteDetail(detailEntity)
    }

    fun convertToResponse(entity: MovieDetailEntity) = MovieDetailResponse(
        title = entity.title,
        year = entity.year,
        rated = entity.rated,
        released = entity.released,
        runtime = entity.runtime,
        genre = entity.genre,
        director = entity.director,
        writer = entity.writer,
        actors = entity.actors,
        plot = entity.plot,
        language = entity.language,
        country = entity.country,
        awards = entity.awards,
        poster = entity.poster,
        ratings = null,
        metascore = entity.metascore,
        imdbRating = entity.imdbRating,
        imdbVotes = entity.imdbVotes,
        imdbID = entity.imdbID,
        type = entity.type,
        dVD = entity.dVD,
        boxOffice = entity.boxOffice,
        production = entity.production,
        website = entity.website,
        response = entity.response
    )

    fun convertToEntity(detailResponse: MovieDetailResponse) = MovieDetailEntity(
        imdbID = detailResponse.imdbID,
        title = detailResponse.title,
        year = detailResponse.year,
        rated = detailResponse.rated,
        released = detailResponse.released,
        runtime = detailResponse.runtime,
        genre = detailResponse.genre,
        director = detailResponse.director,
        writer = detailResponse.writer,
        actors = detailResponse.actors,
        plot = detailResponse.plot,
        language = detailResponse.language,
        country = detailResponse.country,
        awards = detailResponse.awards,
        poster = detailResponse.poster,
        metascore = detailResponse.metascore,
        imdbRating = detailResponse.imdbRating,
        imdbVotes = detailResponse.imdbVotes,
        type = detailResponse.type,
        dVD = detailResponse.dVD,
        boxOffice = detailResponse.boxOffice,
        production = detailResponse.production,
        website = detailResponse.website,
        response = detailResponse.response
    )
}