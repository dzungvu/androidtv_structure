package com.thedung.androidtvstructure.data.models.entities.auth

data class RefreshTokenEntity (
    val result: String,
    val refreshToken: String,
    val token: String
)