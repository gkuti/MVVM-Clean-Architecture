package com.example.data.util

import com.example.domain.util.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart


fun Job?.cancelIfActive(): Boolean {
    if (this?.isActive == true) {
        cancel()
        return true
    }
    return false
}

fun <T : Any> Flow<Result<T>>.applyCommonSideEffects() =
    onStart { emit(Result.Progress) }