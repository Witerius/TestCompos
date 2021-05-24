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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import com.example.testcompose.network.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

data class ProducerResult<T>(
    val result: State<T>,
    val onRefresh: () -> Unit,
    val onClearError: () -> Unit
)

@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    block: suspend Producer.() -> ResultState<T>
): ProducerResult<UiState<T>> = produceUiState(producer, Unit, block)

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun <Producer, T> produceUiState(
    producer: Producer,
    key: Any?,
    block: suspend Producer.() -> ResultState<T>
): ProducerResult<UiState<T>> {

    val refreshChannel = remember { Channel<Unit>(Channel.CONFLATED) }
    val errorClearChannel = remember { Channel<Unit>(Channel.CONFLATED) }

    val result = produceState(UiState<T>(loading = true), producer, key) {

        value = UiState(loading = true)
        refreshChannel.send(Unit)

        launch {
            for (clearEvent in errorClearChannel) {
                value = value.copy(error = null)
            }
        }

        for (refreshEvent in refreshChannel) {
            value = value.copy(loading = true)
            value = value.copyWithResult(producer.block())
        }
    }

    return ProducerResult(
        result = result,
        onRefresh = { refreshChannel.offer(Unit) },
        onClearError = { errorClearChannel.offer(Unit) }
    )
}
