package com.example.testcompose.ui.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.testcompose.utils.composable.produceUiState
import org.koin.java.KoinJavaComponent.inject

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel by inject(HomeViewModel::class.java)
    val responseObserver = viewModel.response.observeAsState()

    viewModel.loadData()



  /*  val (postUiState, refreshPost, clearError) = produceUiState(viewModel) {
        loadData()
    }*/
}