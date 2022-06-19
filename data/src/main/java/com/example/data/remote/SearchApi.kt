package com.example.data.remote

import com.example.domain.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/breeds/search")
    suspend fun searchBreeds(@Query("q") query: String): Response<List<Breed>>
}