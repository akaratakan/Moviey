package com.aa.local.di

import android.content.Context
import androidx.room.Room
import com.aa.local.common.AppDatabase
import com.aa.local.common.MovieDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by atakanakar on 16.08.2023
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "movie_db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }

}