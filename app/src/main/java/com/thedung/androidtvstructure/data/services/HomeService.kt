package com.thedung.androidtvstructure.data.services

import com.thedung.androidtvstructure.classes.network_adapter.Response
import com.thedung.androidtvstructure.data.models.responses.home.HomeGroupResponse
import retrofit2.http.GET


interface HomeService {
    @GET("items/")
    suspend fun getHome(): Response<List<HomeGroupResponse>>
}