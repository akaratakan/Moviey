package com.aa.local.common

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun movieDao(): MovieDao
}