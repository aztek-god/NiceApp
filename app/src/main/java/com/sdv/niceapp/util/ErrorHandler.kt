package com.sdv.niceapp.util

import androidx.annotation.IntDef
import retrofit2.HttpException
import java.lang.annotation.RetentionPolicy


enum class ErrorCode(val code: Int) {
    NOT_FOUND(404), UNKNOWN(-1);

    companion object {
        fun getErrorByCode(code: Int): ErrorCode {
            values().forEach { errorCode ->
                if (errorCode.code == code) {
                    return errorCode
                }
            }

            return UNKNOWN
        }
    }
}

fun handleError(error: Throwable): ErrorCode {
    when (error) {
        is HttpException -> {
            ErrorCode.getErrorByCode(error.code())
        }
    }

    return ErrorCode.UNKNOWN
}