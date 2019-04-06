package com.sdv.niceapp.util

sealed class Result<T> {
    data class Progress<T>(var isLoading: Boolean) : Result<T>()

    data class Error<T>(val e: Throwable) : Result<T>()

    data class Data<T>(val data: T) : Result<T>()

    class Empty<T> : Result<T>()

    companion object {
        fun <T> createProgress(isLoading: Boolean) = Progress<T>(isLoading)
        fun <T> createError(e: Throwable) = Error<T>(e)
        fun <T> createData(data: T) = Data(data)
        fun <T> createEmpty() = Empty<T>()
    }
}