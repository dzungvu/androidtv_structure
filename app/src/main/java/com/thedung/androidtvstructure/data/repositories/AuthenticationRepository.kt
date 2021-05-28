package com.thedung.androidtvstructure.data.repositories

import com.thedung.androidtvstructure.classes.network_adapter.NetworkResponse
import com.thedung.androidtvstructure.classes.network_adapter.models.Resource
import com.thedung.androidtvstructure.data.models.entities.auth.RefreshTokenEntity
import com.thedung.androidtvstructure.data.models.requests.auth.RefreshTokenRequest
import com.thedung.androidtvstructure.data.services.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationService: AuthenticationService
) {
    fun refreshToken(request: RefreshTokenRequest) = flow<Resource<RefreshTokenEntity>> {
        when (val response = authenticationService.refreshToken(request)) {
            is NetworkResponse.Success -> {
                emit(Resource.Success(response.body.toEntity()))
            }
            else -> {
                throw response.toError()
            }
        }
    }.catch {
        it.printStackTrace()
        emit(Resource.Error())
    }.flowOn(Dispatchers.IO)
}