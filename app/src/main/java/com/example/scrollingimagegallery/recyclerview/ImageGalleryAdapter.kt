package com.example.scrollingimagegallery.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.scrollingimagegallery.R
import com.example.scrollingimagegallery.utils.ImageHandler

class ImageGalleryAdapter(
    private val imageHandler: ImageHandler
) : ListAdapter<ImageGalleryData, ImageGalleryAdapter.ImageViewHolder>(
    DiffCallback()
) {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.galleryImageView)
        private val saveButton: Button = itemView.findViewById(R.id.saveButton)

        fun bind(imageData: ImageGalleryData) {
            Glide.with(itemView.context)
                .load(imageData.download_url)
                .placeholder(CircularProgressDrawable(itemView.context).apply {
                    centerRadius = 30f
                    strokeWidth = 5f
                    start()
                })
                .into(imageView)

            saveButton.setOnClickListener {
                imageHandler.saveImage(imageData.download_url, imageData.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gallery_item_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ImageGalleryData>() {
        override fun areItemsTheSame(
            oldItem: ImageGalleryData,
            newItem: ImageGalleryData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ImageGalleryData,
            newItem: ImageGalleryData
        ): Boolean {
            return oldItem == newItem
        }
    }
}
