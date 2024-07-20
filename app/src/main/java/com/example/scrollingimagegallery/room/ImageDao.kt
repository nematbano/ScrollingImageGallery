package com.example.scrollingimagegallery.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM images")
    fun getAllImages(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(images: List<ImageEntity>)

    @Query("DELETE FROM images")
     fun clearAll()
}