/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.testcompose.utils.composable

import com.example.testcompose.network.ResultState
import kz.viled.domain.entities.BaseErrorResponse

data class UiState<T>(
    val loading: Boolean = false,
    val error: BaseErrorResponse? = null,
    val data: T? = null
) {

    val hasError: Boolean
        get() = error != null

    val initialLoad: Boolean
        get() = data == null && loading && !hasError
}

fun <T> UiState<T>.copyWithResult(value: ResultState<T>): UiState<T> {
    return when (value) {
        is ResultState.Success -> copy(loading = false, error = null, data = value.data)
        is ResultState.Error -> copy(loading = false, error = value.errorResponse)
        else -> copy()
    }
}
