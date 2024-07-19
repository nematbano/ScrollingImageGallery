package com.example.scrollingimagegallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageGalleryAdapter : ListAdapter<ImageGalleryData, ImageGalleryAdapter.ImageViewHolder>(
    DiffCallback()
) {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.galleryImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)

    }

    class DiffCallback : DiffUtil.ItemCallback<ImageGalleryData>() {
        override fun areItemsTheSame(oldItem: ImageGalleryData, newItem: ImageGalleryData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageGalleryData, newItem: ImageGalleryData): Boolean {
            return oldItem == newItem
        }
    }
}
