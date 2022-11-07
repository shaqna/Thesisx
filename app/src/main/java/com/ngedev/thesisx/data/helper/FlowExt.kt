package com.ngedev.thesisx.data.helper

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first

suspend fun <Result, To> Flow<FirebaseResponse<Result>>.first(
    emitter: FlowCollector<FirebaseResponse<To>>,
    onDataReceive: suspend (result: Result) -> Unit
) {
    this.first { response ->
        when (response) {
            is FirebaseResponse.Success -> {
                onDataReceive(response.data)
            }
            is FirebaseResponse.Error -> {
                emitter.emit(FirebaseResponse.Error(response.errorMessage))
            }
            FirebaseResponse.Empty -> {
                emitter.emit(FirebaseResponse.Empty)
            }
        }
        true
    }
}