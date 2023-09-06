package com.aa.local.entities.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by atakanakar on 16.08.2023
 */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: MovieDetailEntity)

    @Query("SELECT * FROM movie_details WHERE imdbID = :imdbId")
    fun fetchMovieWithId(imdbId: String): MovieDetailEntity?

    @Query("SELECT * FROM movie_details")
    fun fetchAll(): List<MovieDetailEntity>?

    @Delete
    fun deleteDetail(movie: MovieDetailEntity)
}