package com.thedung.androidtvstructure.data.models.requests.auth

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshtoken")
    val refreshToken: String
)