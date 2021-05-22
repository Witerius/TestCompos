package com.example.testcompose.domain.usecases

import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kz.viled.domain.entities.BaseErrorResponse
import retrofit2.Response

abstract class  BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async {
            run(params)
        }
        scope.launch { onResult(backgroundJob.await()) }
    }

    fun <T> onError(response: Response<T>): Either.Left<Failure.FeatureFailure> {
        val gson = GsonBuilder().create()
        val errorBody = gson.fromJson(response.errorBody()?.string(), BaseErrorResponse::class.java) ?: BaseErrorResponse()

        if(response.code() == 401) {
            errorBody.code = 401
        }

        return Either.Left(Failure.FeatureFailure(featureError = errorBody))
    }
}