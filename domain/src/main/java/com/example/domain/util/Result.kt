package com.example.domain.util

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Failure(val exception: Throwable?) : Result<Nothing>()
    object Progress : Result<Nothing>()

    fun getResultOrNull(): T? {
        return when (this) {
            is Success -> this.data
            else -> null
        }
    }

    fun getErrorOrNull(): Throwable? {
        return when (this) {
            is Failure -> this.exception
            else -> null
        }
    }

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure
    fun isProgress(): Boolean = this is Progress

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Failure -> "Failure[exception=$exception]"
            is Progress -> "In Progress"
        }
    }
}



