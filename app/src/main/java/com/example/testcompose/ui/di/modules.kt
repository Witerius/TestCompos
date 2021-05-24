package com.example.testcompose.ui.di

import com.example.testcompose.domain.repositories.TestAppRepository
import com.example.testcompose.domain.repositories.TestAppRepositoryImpl
import com.example.testcompose.domain.usecases.TestAppUseCase
import com.example.testcompose.ui.pages.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositories = module{
    single<TestAppRepository> { TestAppRepositoryImpl(get()) }
}

val useCase = module {
    factory { TestAppUseCase(get()) }
}

val viewModule = module {
    viewModel { HomeViewModel(get()) }
}