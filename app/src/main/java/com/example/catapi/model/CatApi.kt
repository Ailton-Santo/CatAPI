package com.example.catapi.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatApi {

    @Headers("x-api-key: 2a125fdc-962a-4a38-8528-36fb11296b44")
    @GET("v1/images/search?order=random&limit=100")
    fun retrieveImages(@Query("mime_types") mimeType: String): Single<List<ImageEntity>>

}