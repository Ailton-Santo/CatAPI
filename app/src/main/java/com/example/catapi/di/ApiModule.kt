package com.example.catapi.di

import com.example.catapi.model.CatApi
import com.example.catapi.model.ImagesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.thecatapi.com/"

    @Provides
    fun provideCatApi(): CatApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Converts Json code into Kotlin code
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    @Provides
    fun provideCatService(): ImagesService {
        return ImagesService()
    }
}