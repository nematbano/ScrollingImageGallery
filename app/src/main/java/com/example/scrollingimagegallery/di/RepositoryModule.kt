package com.example.scrollingimagegallery.di

import com.example.scrollingimagegallery.network.PicSumService
import com.example.scrollingimagegallery.repo.ImageRepository
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
    fun provideImageRepository(service: PicSumService): ImageRepository {
        return ImageRepository(service)
    }
}