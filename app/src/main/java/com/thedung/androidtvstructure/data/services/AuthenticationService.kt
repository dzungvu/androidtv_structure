package com.thedung.androidtvstructure.data.services

import com.thedung.androidtvstructure.classes.network_adapter.Response
import com.thedung.androidtvstructure.data.models.requests.auth.RefreshTokenRequest
import com.thedung.androidtvstructure.data.models.responses.auth.RefreshTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthenticationService {
    @POST("auth/refreshtoken/")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<RefreshTokenResponse>
}