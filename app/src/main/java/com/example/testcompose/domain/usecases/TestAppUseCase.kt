package com.example.testcompose.domain.usecases

import com.example.testcompose.domain.entities.TestAppResponse
import com.example.testcompose.domain.repositories.TestAppRepository
import retrofit2.Response

class TestAppUseCase(
    private val testAppRepository: TestAppRepository
) : BaseUseCase<Response<TestAppResponse>, Unit>() {

    override suspend fun run(params: Unit): Either<Failure, Response<TestAppResponse>> {
        return try {
            val response = testAppRepository.getItems()
            if (response.isSuccessful) {
                Either.Right(response)
            } else {
                onError(response)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(Failure.NetworkConnection)
        }
    }

}