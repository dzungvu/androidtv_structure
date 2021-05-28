package com.thedung.androidtvstructure.data.models.responses.auth

import com.google.gson.annotations.SerializedName
import com.thedung.androidtvstructure.data.models.entities.auth.RefreshTokenEntity

data class RefreshTokenResponse(
    @SerializedName("result")
    val result: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("refreshtoken")
    val refreshToken: String?,
    @SerializedName("errorcode")
    val errorCode: String?,
    @SerializedName("error")
    val msg: String?
) {
    fun toEntity(): RefreshTokenEntity {
        return RefreshTokenEntity(
            result ?: "", refreshToken ?: "", token ?: ""
        )
    }
}