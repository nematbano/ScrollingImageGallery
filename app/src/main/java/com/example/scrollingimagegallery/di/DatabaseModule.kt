package com.example.scrollingimagegallery.di

import android.content.Context
import androidx.room.Room
import com.example.scrollingimagegallery.room.AppDatabase
import com.example.scrollingimagegallery.room.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "image_database"
        ).build()
    }

    @Provides
    fun provideImageDao(db: AppDatabase): ImageDao {
        return db.imageDao()
    }

}