package com.example.scrollingimagegallery.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageHandler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun saveImage(url: String, imageId: String): Uri? {
        var savedUri: Uri? = null
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    savedUri = saveImageToStorage(resource, imageId)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        return savedUri
    }

    private fun saveImageToStorage(bitmap: Bitmap, imageId: String): Uri? {
        var savedImagePath: String? = null

        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/ScrollingImageGallery"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, "$imageId.jpg")
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath)
            val authority = "${context.packageName}.provider"
            return FileProvider.getUriForFile(context, authority, imageFile)
        }
        return null
    }

    private fun galleryAddPic(imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.setData(contentUri)
        context.sendBroadcast(mediaScanIntent)
    }

}
