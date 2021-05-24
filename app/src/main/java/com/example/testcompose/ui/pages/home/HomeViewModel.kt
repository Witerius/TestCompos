package com.example.testcompose.ui.pages.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcompose.domain.entities.TestAppResponse
import com.example.testcompose.domain.usecases.Failure
import com.example.testcompose.domain.usecases.TestAppUseCase
import com.example.testcompose.network.ResultState
import com.example.testcompose.utils.ext.setSuccess
import retrofit2.Response

class HomeViewModel(
    private val testAppUseCase: TestAppUseCase
) : ViewModel() {

    val response = MutableLiveData<ResultState<TestAppResponse>>()
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