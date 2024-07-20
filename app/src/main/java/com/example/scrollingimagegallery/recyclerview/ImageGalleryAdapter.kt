package com.example.scrollingimagegallery.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.scrollingimagegallery.R

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
        val item = getItem(position)
        val placeholder = CircularProgressDrawable(holder.itemView.context)
            .apply {
                centerRadius = 30f
                strokeWidth = 5f
                start()
            }

        Glide.with(holder.itemView.context)
            .load(item.download_url)
            .placeholder(placeholder)
            .error(R.drawable.ic_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

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
