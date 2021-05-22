package com.example.testcompose.domain.repositories

import com.example.testcompose.domain.entities.TestAppResponse
import com.example.testcompose.network.Api
import retrofit2.Response

interface TestAppRepository {
    suspend fun getItems(): Response<TestAppResponse>
}

class TestAppRepositoryImpl(private val api: Api) : TestAppRepository {
    override suspend fun getItems(): Response<TestAppResponse> {
        return api.getItems()
    }

}