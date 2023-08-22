package com.aa.local.entities.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @Json(name = "name")
    @ColumnInfo(name = "name")
    val name: String,
    @Json(name = "desc")
    @ColumnInfo(name = "desc")
    val desc: String,
    @Json(name = "imageUrl")
    @ColumnInfo(name = "imageUrl")
    val url: String
)