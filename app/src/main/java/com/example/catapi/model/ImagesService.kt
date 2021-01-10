package com.example.catapi.model

import com.example.catapi.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class ImagesService {
    @Inject
    lateinit var api: CatApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun retrieveImages(mimeType: String): Single<List<ImageEntity>> {
        return api.retrieveImages(mimeType)

    }
}