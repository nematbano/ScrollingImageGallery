package com.example.scrollingimagegallery.repo

import com.example.scrollingimagegallery.network.PicSumService
import com.example.scrollingimagegallery.recyclerview.ImageGalleryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepository @Inject constructor(private val service: PicSumService) {
    fun getImages(page: Int, limit: Int): Flow<List<ImageGalleryData>> = flow {
        val images = service.getImages(page, limit)
        emit(images)
    }
}