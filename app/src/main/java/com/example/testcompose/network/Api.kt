package com.example.testcompose.network

import com.example.testcompose.domain.entities.TestAppResponse
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("testapp/items.json")
    suspend fun getItems(): Response<TestAppResponse>

}