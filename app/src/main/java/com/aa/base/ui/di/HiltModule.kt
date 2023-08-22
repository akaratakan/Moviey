package com.aa.base.ui.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    /*@Provides
    fun provideGlide(@ActivityContext context: Context): RequestManager = Glide.with(context)
*/
}