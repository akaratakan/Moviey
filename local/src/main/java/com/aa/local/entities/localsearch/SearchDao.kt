package com.aa.local.entities.localsearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by atakanakar on 20.08.2023
 */
@Dao
interface SearchDao {

    @Query("SELECT * FROM local_search")
    fun getAllSearchedText(): Flow<List<LocalSearch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalSearch(localSearch: LocalSearch)

}