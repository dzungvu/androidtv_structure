package com.thedung.androidtvstructure.classes.auth

import com.thedung.androidtvstructure.classes.application.AppConfig
import com.thedung.androidtvstructure.classes.network_adapter.models.Resource
import com.thedung.androidtvstructure.data.models.requests.auth.RefreshTokenRequest
import com.thedung.androidtvstructure.data.repositories.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationHelper @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val appConfig: AppConfig
) {
    @Synchronized
    suspend fun getNewToken(oldToken: String): String {
        return withContext(Dispatchers.Default) {
            if (oldToken == appConfig.token) {
                when (val response = authenticationRepository.refreshToken(
                    RefreshTokenRequest(
                        appConfig.refreshToken
                    )
                ).first()) {
                    is Resource.Success -> {
                        response.data?.run {
                            when (result) {
                                "1" -> {
                                    appConfig.token = token
                                    appConfig.refreshToken = refreshToken
                                    token
                                }
                                else -> oldToken
                            }
                        } ?: oldToken
                    }
                    else -> oldToken
                }
            } else appConfig.token
        }
    }
}