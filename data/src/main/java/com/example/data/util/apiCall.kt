package com.example.data.util

import com.example.domain.util.Result
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        call().run {
            if (isSuccessful) {
                return Result.Success(body())
            }
            return Result.Failure(Throwable("${this.code()}: ${this.message()}"))
        }
    } catch (e: Exception) {
        Result.Failure(IOException(e))
    }
}