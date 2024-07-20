package com.example.scrollingimagegallery.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.scrollingimagegallery.utils.ConnectionManager
import com.example.scrollingimagegallery.network.PicSumService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun providePicSumService(retrofit: Retrofit): PicSumService {
        return retrofit.create(PicSumService::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideConnectionManager(connectivityManager: ConnectivityManager): ConnectionManager {
        return ConnectionManager(connectivityManager)
    }
}