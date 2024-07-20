package com.example.scrollingimagegallery.di

import com.example.scrollingimagegallery.utils.ConnectionManager
import com.example.scrollingimagegallery.network.PicSumService
import com.example.scrollingimagegallery.repo.ImageRepository
import com.example.scrollingimagegallery.room.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(service: PicSumService, connectionManager: ConnectionManager, imageDao: ImageDao): ImageRepository {
        return ImageRepository(service, connectionManager, imageDao)
    }
}