@file:Suppress("unused")

package com.thedung.androidtvstructure.classes.network_adapter.models

import com.google.gson.annotations.SerializedName

data class DataResponse<DATA>(
    @SerializedName("result")
    val result: Int = 0,
    @SerializedName("data")
    val data: DATA?,
    @SerializedName("error")
    val error: Error?
)

data class Error(
    @SerializedName("code", alternate = ["status_code"])
    val code: String?,
    @SerializedName("message", alternate = ["status_message"])
    val message: String?
) {
    fun getErrorCode(): Int {
        return code?.toInt() ?: 0
    }
}
