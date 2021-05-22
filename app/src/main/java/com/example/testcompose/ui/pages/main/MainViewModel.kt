package com.example.testcompose.ui.pages.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcompose.domain.entities.TestAppResponse
import com.example.testcompose.domain.usecases.Failure
import com.example.testcompose.domain.usecases.TestAppUseCase
import com.example.testcompose.network.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import com.example.testcompose.utils.ext.setSuccess
import retrofit2.Response

class MainViewModel(
    private val testAppUseCase: TestAppUseCase
) : ViewModel() {

    private val response = MutableLiveData<ResultState<TestAppResponse>>()
    val loading = Transformations.map(response) { it is ResultState.Loading }

    fun loadData() {
        /*viewModelScope.launch(Dispatchers.IO) {

        }*/
        testAppUseCase(viewModelScope, Unit) {
            it.either(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleSuccess(data: Response<TestAppResponse>) {
        data.body()?.let {
            response.setSuccess(it)
        }
    }

    private fun handleFailure(f: Failure) {

    }
}