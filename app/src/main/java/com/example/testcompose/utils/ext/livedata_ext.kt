package com.example.testcompose.utils.ext

import androidx.lifecycle.MutableLiveData
import com.example.testcompose.domain.usecases.Failure
import com.example.testcompose.network.ResultState
import kz.viled.domain.entities.BaseErrorResponse

//SUCCESS

fun <T> MutableLiveData<ResultState<T>>.setSuccess(data: T) =
    postValue(ResultState.Success(data))

fun <T> MutableLiveData<ResultState<T>>.setSuccess() = //SuccessVoid не тоже самое, что Empty. это для случая, когда нужно сменить статус с Loading, но при этом не обновлять данные в адаптере.
    postValue(ResultState.SuccessVoid(null))

fun <T> MutableLiveData<ResultState<T>>.setSuccessWithoutNotification(data: T) = //это для случаев, когда нужно обновить содержимое, но не отсылать уведомление в обзервер для ResultState.Success
    postValue(ResultState.SuccessVoid(data))

fun <T> MutableLiveData<ResultState<T>>.setSuccessDeleting(position: Int) =
    postValue(ResultState.SuccessDeleting(position))

fun <T> MutableLiveData<ResultState<T>>.setSuccessPagination(data: T) =
    postValue(ResultState.SuccessPagination(data))

fun <T> MutableLiveData<ResultState<T>>.setEmpty() =
    postValue(ResultState.Empty(null))

//LOADING

fun <T> MutableLiveData<ResultState<T>>.setLoading() =
    postValue(ResultState.Loading(null))

//ERROR
fun <T> MutableLiveData<ResultState<T>>.setError(f: Failure) =
    postValue(ResultState.Error(f.error))

fun <T> MutableLiveData<ResultState<T>>.setError() =
    postValue(ResultState.Error(BaseErrorResponse()))

fun <T> MutableLiveData<ResultState<T>>.setNotFound() =
    postValue(ResultState.NotFound(null))

fun <T> MutableLiveData<ResultState<T>>.setNeedAuth() =
    postValue(ResultState.NeedAuth(null))
