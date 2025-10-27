package com.example.summitapp.view.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.summitapp.R

class ImageSliderAdapter(
    private val imageUrls: List<String>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]

        Glide.with(holder.imageView.context)
            .load(imageUrl)
            .error(R.drawable.dummy_category)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = imageUrls.size
}
