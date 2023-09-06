package com.aa.local.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aa.local.entities.localsearch.LocalSearch
import com.aa.local.entities.localsearch.SearchDao
import com.aa.local.entities.movie.MovieDao
import com.aa.local.entities.movie.MovieDetailEntity

@Database(entities = [MovieDetailEntity::class, LocalSearch::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun localSearchDao(): SearchDao
}