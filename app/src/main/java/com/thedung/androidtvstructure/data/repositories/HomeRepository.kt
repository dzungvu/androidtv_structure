package com.thedung.androidtvstructure.data.repositories

import com.thedung.androidtvstructure.classes.extension.apiFlow
import com.thedung.androidtvstructure.classes.network_adapter.NetworkResponse
import com.thedung.androidtvstructure.classes.network_adapter.models.Resource
import com.thedung.androidtvstructure.data.models.responses.home.toEntity
import com.thedung.androidtvstructure.data.services.HomeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val service: HomeService
) {

    fun getHome() = apiFlow {
        when (val response = service.getHome()) {
            is NetworkResponse.Success -> {
                emit(Resource.Success(response.body.toEntity()))
            }
            else -> {
                throw response.toError()
            }
        }
    }
}