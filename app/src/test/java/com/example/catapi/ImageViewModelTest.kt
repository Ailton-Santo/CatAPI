package com.example.catapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.catapi.model.CategoryEntity
import com.example.catapi.model.ImageEntity
import com.example.catapi.model.ImagesService
import com.example.catapi.viewmodel.ImageViewModel
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ImageViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var  imagesService: ImagesService

    @InjectMocks
    private val imageViewModel = ImageViewModel()

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor{it.run()})
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun `WHEN server returns cat list THEN viewModel has the same list`() {
        val catImage = ImageEntity(url = "url", listOf())
        val catList = listOf(catImage)
        val testSingle = Single.just(catList)
        whenever(imagesService.retrieveImages(anyOrNull())).thenReturn(testSingle)

        imageViewModel.refresh()

        Assert.assertEquals(catList, imageViewModel.images.value)
    }

    @Test
    fun `WHEN server call fails THEN viewModel has no images`() {
        val testSingle = Single.error<List<ImageEntity>>(Throwable())
        whenever(imagesService.retrieveImages(anyOrNull())).thenReturn(testSingle)

        imageViewModel.refresh()

        Assert.assertEquals(null, imageViewModel.images.value)
    }

    @Test
    fun `WHEN some images have "hat" category THEN they get filtered out`() {
        val catImage = ImageEntity(url = "url", listOf(CategoryEntity("catfood")))
        val catHatImage = ImageEntity(url = "url", listOf(CategoryEntity("hat")))
        val catList = listOf(catImage, catHatImage)
        val testSingle = Single.just(catList)
        whenever(imagesService.retrieveImages(anyOrNull())).thenReturn(testSingle)

        imageViewModel.refresh()

        val filtered = listOf(catImage)
        Assert.assertEquals(filtered, imageViewModel.images.value)
    }

    @Test
    fun `WHEN image has no categories THEN it doesn't get filtered out`() {
        val catImage = ImageEntity(url = "url", null)
        val catList = listOf(catImage)
        val testSingle = Single.just(catList)
        whenever(imagesService.retrieveImages(anyOrNull())).thenReturn(testSingle)

        imageViewModel.refresh()

        Assert.assertEquals(catList, imageViewModel.images.value)
    }
}