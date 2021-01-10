package com.example.catapi.di

import com.example.catapi.model.ImagesService
import com.example.catapi.viewmodel.ImageViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: ImagesService)

    fun inject(viewModel: ImageViewModel)
}