package com.example.scrollingimagegallery.repo

import com.example.scrollingimagegallery.utils.ConnectionManager
import com.example.scrollingimagegallery.network.PicSumService
import com.example.scrollingimagegallery.recyclerview.ImageGalleryData
import com.example.scrollingimagegallery.room.ImageDao
import com.example.scrollingimagegallery.room.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepository @Inject constructor(private val service: PicSumService, private val connectionManager: ConnectionManager, private val imageDao: ImageDao,) {

private val allImages = mutableListOf<ImageEntity>()
    suspend fun getImages(page: Int, limit: Int): Result<List<ImageGalleryData>> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                    if (connectionManager.isOnline()) {
                        // Fetch from network
                        val response = service.getImages(page, limit)
                        val newImageEntities = response.map {ImageEntity(it.id,it.author, it.width, it.height,it.url, it.download_url) }

                        // Save images to local database
                        imageDao.insertAll(newImageEntities)
                        allImages.addAll(newImageEntities)
                    } else {
                        // Load images from local database
                        if (allImages.isEmpty()) {
                            allImages.addAll(imageDao.getAllImages())
                        }
                    }

                    // Convert ImageEntity to ImageData
                    val imageDataList = allImages.map { ImageGalleryData(it.id,it.author, it.width, it.height,it.url, it.download_url) }
                    Result.success(imageDataList)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun reset() {
        withContext(Dispatchers.IO) {
            imageDao.clearAll()
            allImages.clear()
        }
    }
}