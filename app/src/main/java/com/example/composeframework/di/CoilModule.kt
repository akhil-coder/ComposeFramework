package com.example.composeframework.di

import android.app.Application
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {
    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .error(com.example.ui_herolist.R.drawable.error_image)
            .placeholder(com.example.ui_herolist.R.drawable.white_background)
            .availableMemoryPercentage(.25)
            .crossfade(true)
            .build()
    }
}