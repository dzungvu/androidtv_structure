package com.thedung.androidtvstructure.classes.network_adapter.models

@Suppress("unused")
sealed class Resource<T : Any> {
    data class Success<T : Any>(val data: T? = null) : Resource<T>()
    data class Error<T : Any>(val errorMsg: String? = "", val errorCode: String? = "", val data: T? = null) : Resource<T>()
    data class Loading<T : Any>(val data: T? = null) : Resource<T>()
}
