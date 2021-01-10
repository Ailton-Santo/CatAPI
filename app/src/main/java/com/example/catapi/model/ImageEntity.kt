package com.example.catapi.model

import com.google.gson.annotations.SerializedName

class ImageEntity (
    @SerializedName("url")
    val url: String,
    @SerializedName("categories")
    val categoryEntities: List<CategoryEntity>?
)