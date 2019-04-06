package com.sdv.niceapp.util

sealed class Result<T> {
    data class Progress<T>(var isLoading: Boolean) : Result<T>()

    data class Error<T>(val e: Throwable) : Result<T>()

    data class Data<T>(val data: T, val isCompleted: Boolean = false) : Result<T>()

    object Empty : Result<Unit>()
}