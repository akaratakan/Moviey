package com.aa.network.di

import android.content.Context
import com.aa.network.BuildConfig
import com.aa.network.api.MovieApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by atakanakar on 29/03/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    fun provideAppContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }


    @Provides
    @Singleton
    fun provideMovieRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)

}