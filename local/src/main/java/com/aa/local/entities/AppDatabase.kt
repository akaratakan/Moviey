package com.aa.local.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aa.local.entities.localsearch.LocalSearch
import com.aa.local.entities.localsearch.SearchDao
import com.aa.local.entities.movie.Movie
import com.aa.local.entities.movie.MovieDao

@Database(entities = [Movie::class, LocalSearch::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun localSearchDao(): SearchDao
}