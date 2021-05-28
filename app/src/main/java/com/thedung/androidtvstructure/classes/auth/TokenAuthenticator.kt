package com.thedung.androidtvstructure.classes.auth

import com.thedung.androidtvstructure.di.module.NetworkModule
import com.thedung.androidtvstructure.utils.LogUtil
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authenticationHelper: AuthenticationHelper
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        LogUtil.e("TokenAuthenticator", "authenticate")
        return runBlocking {
            val oldToken = response.request.header(NetworkModule.AUTHORIZATION)
            if (!oldToken.isNullOrBlank()) {
                val newToken = authenticationHelper.getNewToken(oldToken)
                if (newToken == oldToken)
                    null
                else
                    response.request.newBuilder()
                        .header(NetworkModule.AUTHORIZATION, newToken)
                        .build()
            } else null
        }
    }

}