package com.aa.local.common

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

/**
 * Created by atakanakar on 16.08.2023
 */
@Dao
interface MovieDao {
    @Insert
    fun insertAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)
}