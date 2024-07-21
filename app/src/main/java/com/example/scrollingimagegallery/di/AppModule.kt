package com.example.scrollingimagegallery.di

import android.content.Context
import com.example.scrollingimagegallery.utils.ImageHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageHandler(@ApplicationContext context: Context): ImageHandler {
        return ImageHandler(context)
    }
}