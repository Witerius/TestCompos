package com.example.testcompose.network

import kz.viled.domain.entities.BaseErrorResponse

/**
 * A wrapper for database and network states.
 */
sealed class ResultState<T> {

    //SUCCESS

    data class Success<T>(val data: T) : ResultState<T>()

    data class SuccessVoid<T>(var data: T?) : ResultState<T>() //это для случая, когда нужно сменить статус с Loading, но при этом не обновлять данные в адаптере.

    data class SuccessPagination<T>(var data: T) : ResultState<T>()

    data class SuccessDeleting<T>(var position: Int, var data: T? = null) : ResultState<T>()

    data class Empty<T>(val data: T?) : ResultState<T>()


    //LOADING

    data class Loading<T>(val data: T?) : ResultState<T>()

    //ERROR

    data class Error<T>(val errorResponse: BaseErrorResponse?) : ResultState<T>()

    data class NeedAuth<T>(var data: T? = null) : ResultState<T>()

    data class NotFound<T>(var data: T?) : ResultState<T>()

}