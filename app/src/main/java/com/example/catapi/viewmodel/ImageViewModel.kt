package com.example.catapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catapi.di.DaggerApiComponent
import com.example.catapi.model.ImageEntity
import com.example.catapi.model.ImagesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageViewModel : ViewModel() {
    private companion object {
        private val GIF_MIME_TYPE = "gif"
        private val STATIC_MIME_TYPE = "jpg,png"
        private val BOTH_MIME_TYPE = "gif,jpg,png"
        private val DEFAULT_MIME_TYPE = GIF_MIME_TYPE
        private val FILTER = "hat"
    }

    @Inject
    lateinit var imagesService: ImagesService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val typeIndex = MutableLiveData<Int>()
    val images = MutableLiveData<List<ImageEntity>>()

    fun refresh() {
        fetchImages(getMimeTypeFromIndex(typeIndex.value))
    }

    private fun getMimeTypeFromIndex(index: Int?): String {
        return when (index) {
            1 -> GIF_MIME_TYPE
            2 -> STATIC_MIME_TYPE
            3 -> BOTH_MIME_TYPE
            else -> DEFAULT_MIME_TYPE
        }
    }

    private fun fetchImages(mimeType: String) {
        disposable.add(
            imagesService.retrieveImages(mimeType)
                .subscribeOn(Schedulers.io())  // Asynchronous programming
                .observeOn(AndroidSchedulers.mainThread()) // displaying the content on the Main Thread
                .subscribeWith(object :
                    DisposableSingleObserver<List<ImageEntity>>() { // define what we going to do with the values after retrieving them
                    override fun onSuccess(value: List<ImageEntity>) {
                        images.value = filterImages(value)
                    }

                    override fun onError(e: Throwable?) {
                        println("Error")
                    }
                })
        )
    }

    private fun filterImages(unfilteredImages: List<ImageEntity>): List<ImageEntity> {
        val filteredList = unfilteredImages.filter { imageEntity ->
            imageEntity.categoryEntities?.none { categoryEntity ->
                categoryEntity.name.contains(FILTER)
            } ?: true // return true if categoryEntities is null
        }
        return filteredList
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}