package com.example.catapi.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(uri: String) {
    val isGif = uri.endsWith(".gif")
    val request = Glide.with(this)
    if (isGif) {
        request.asGif()
    }
    request
        .load(uri)
        .into(this)
}