package com.thedung.androidtvstructure.classes.network_adapter

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkCall<SUCCESS : Any, ERROR: Any>(
    private val call: Call<SUCCESS>,
    private val errorConverter: Converter<ResponseBody, ERROR>
) : Call<NetworkResponse<SUCCESS, ERROR>> {
    override fun enqueue(callback: Callback<NetworkResponse<SUCCESS, ERROR>>) {
        return call.enqueue(object : Callback<SUCCESS> {
            override fun onResponse(call: Call<SUCCESS>, response: Response<SUCCESS>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                if (response.isSuccessful) {
                    body?.run {
                        callback.onResponse(this@NetworkCall, Response.success(
                            NetworkResponse.Success(
                                body
                            )
                        ))
                    } ?: run {
                        callback.onResponse(this@NetworkCall, Response.success(NetworkResponse.UnknownError()))
                    }
                } else {
                    val errorBody = when {
                        error == null || error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                    errorBody?.run {
                        callback.onResponse(this@NetworkCall, Response.success(
                            NetworkResponse.ApiError(
                                errorBody,
                                code
                            )
                        ))
                    } ?: run {
                        callback.onResponse(this@NetworkCall, Response.success(NetworkResponse.UnknownError()))
                    }
                }
            }

            override fun onFailure(call: Call<SUCCESS>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkResponse.NetworkError(t)
                    else -> NetworkResponse.UnknownError(t)
                }
                callback.onResponse(this@NetworkCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = call.isExecuted

    override fun timeout(): Timeout = call.timeout()

    override fun clone() = NetworkCall(call.clone(), errorConverter)

    override fun isCanceled() = call.isCanceled

    override fun cancel() = call.cancel()

    override fun execute(): Response<NetworkResponse<SUCCESS, ERROR>> {
        throw UnsupportedOperationException("NetworkCall doesn't support execute")
    }

    override fun request(): Request = call.request()
}