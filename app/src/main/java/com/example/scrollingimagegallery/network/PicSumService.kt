package com.example.scrollingimagegallery.network

import com.example.scrollingimagegallery.recyclerview.ImageGalleryData
import retrofit2.http.GET
import retrofit2.http.Query

interface PicSumService {
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageGalleryData>

}