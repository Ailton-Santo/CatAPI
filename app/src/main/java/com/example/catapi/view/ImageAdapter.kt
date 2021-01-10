package com.example.catapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.catapi.R
import com.example.catapi.model.ImageEntity
import com.example.catapi.util.loadImage

class ImageAdapter (var cats: ArrayList<ImageEntity>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    fun updateCats(images: List<ImageEntity>) {
        cats.clear()
        cats.addAll(images)
        notifyDataSetChanged()
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById(R.id.imageView) as ImageView


        fun bind(catImage: ImageEntity) {
            imageView.loadImage(catImage.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImageViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
    )


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(cats[position])
    }

    override fun getItemCount() = cats.size

}