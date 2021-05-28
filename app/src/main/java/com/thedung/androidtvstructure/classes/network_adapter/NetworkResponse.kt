package com.thedung.androidtvstructure.classes.network_adapter

import com.thedung.androidtvstructure.classes.network_adapter.models.Error
import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable? = null) : NetworkResponse<Nothing, Nothing>()

    fun toError(): Throwable {
        return when (this) {
            is NetworkError -> error
            is UnknownError -> error ?: Throwable(message = "Unknown error")
            is ApiError -> {
                if (this.body is Error) {
                    NetworkThrowable(this.body.message, this.body.code)
                } else
                    Throwable(message = "Unknown error")
            }
            else -> Throwable()
        }
    }
}

class NetworkThrowable(val msg: String?, val code: String?) : Throwable(msg)