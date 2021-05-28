package com.thedung.androidtvstructure.di.module

import com.google.gson.Gson
import com.thedung.androidtvstructure.BuildConfig
import com.thedung.androidtvstructure.classes.application.AppConfig
import com.thedung.androidtvstructure.classes.auth.TokenAuthenticator
import com.thedung.androidtvstructure.classes.exceptions.CannotBuildUrlException
import com.thedung.androidtvstructure.classes.exceptions.NetworkTimeoutException
import com.thedung.androidtvstructure.classes.network_adapter.NetworkResponseAdapterFactory
import com.thedung.androidtvstructure.data.services.AuthenticationService
import com.thedung.androidtvstructure.utils.LogUtil
import com.thedung.androidtvstructure.utils.debug
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        const val AUTHORIZATION = "Authorization"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        private const val API_TIME_OUT = 30L
    }

    private external fun getBaseUrl(debug: Boolean): String

    private fun getBaseUrl(): String {
        return getBaseUrl(BuildConfig.DEBUG)
    }

    init {
        System.loadLibrary("app-values")
    }

    /**
     * Handle response 403 case
     */
    private fun handleResponse(
        requestBody: String,
        bodyString: String,
        responseMessage: String,
        path: String
    ) {
        runCatching {
            val json = JSONObject(bodyString)
            val code = json.getInt("errorcode")
            val message = json.getString("error") ?: responseMessage

//            (AppApplication.INSTANCE as AppApplication).forceLogout(message)

        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun providesHttpClient(
        interceptor: Interceptor,
        tokenAuthenticator: TokenAuthenticator? = null
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(API_TIME_OUT, TimeUnit.SECONDS)
        httpClient.addInterceptor(interceptor)
        tokenAuthenticator?.run { httpClient.authenticator(tokenAuthenticator) }
        debug { httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) }
        return httpClient.build()
    }

    @AuthInterceptor
    @Provides
    fun providesAuthInterceptor(appConfig: AppConfig) = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .url(
                chain.request().url.newBuilder(
                    chain.request().url.toString()
                )?.build() ?: throw CannotBuildUrlException()
            )
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .addHeader(AUTHORIZATION, appConfig.token)
            .build()
        val requestBodyBuffer = Buffer()
        request.body?.writeTo(requestBodyBuffer)
        val requestBody = requestBodyBuffer.clone().readUtf8()
        val path = request.url.toString().replace(getBaseUrl(), "")

        try {
            val response = chain.proceed(request)
            val responseMessage = response.message
            val responseCode = response.code

            LogUtil.e("NetworkModule", "code: $responseCode")

            if (!response.isSuccessful) {
                when (responseCode) {
                    403 -> {
                        val source = response.body?.source()
                        source?.request(Long.MAX_VALUE)
                        val responseBodyString = source?.buffer?.clone()?.readUtf8()
                        handleResponse(requestBody, responseBodyString ?: "", responseMessage, path)
                    }
                }
            }
            return@Interceptor response
        } catch (e: SocketTimeoutException) {
            throw NetworkTimeoutException().apply {
                this.requestBody = requestBody
                this.path = path
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @NormalInterceptor
    @Provides
    fun providesNormalInterceptor(appConfig: AppConfig) = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .url(
                chain.request().url.newBuilder(
                    chain.request().url.toString()
                )?.build() ?: throw CannotBuildUrlException()
            )
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .addHeader(AUTHORIZATION, appConfig.token)
            .build()

        val requestBodyBuffer = Buffer()
        request.body?.writeTo(requestBodyBuffer)
        val requestBody = requestBodyBuffer.clone().readUtf8()
        val path = request.url.toString().replace(getBaseUrl(), "")

        try {
            val response = chain.proceed(request)
            val responseMessage = response.message
            val responseCode = response.code

            if (!response.isSuccessful) {
                when (responseCode) {
                    403 -> {
                        val source = response.body?.source()
                        source?.request(Long.MAX_VALUE)
                        val responseBodyString = source?.buffer?.clone()?.readUtf8()
                        handleResponse(requestBody, responseBodyString ?: "", responseMessage, path)
                    }
                }
            }
            return@Interceptor response
        } catch (e: SocketTimeoutException) {
            throw NetworkTimeoutException().apply {
                this.requestBody = requestBody
                this.path = path
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @AuthClient
    @Provides
    fun providesAuthClient(
        @AuthInterceptor interceptor: Interceptor,
        gson: Gson,
        adapterFactory: NetworkResponseAdapterFactory,
        tokenAuthenticator: TokenAuthenticator
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(providesHttpClient(interceptor, tokenAuthenticator))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(adapterFactory)
            .build()
    }

    @NormalClient
    @Provides
    fun providesNormalClient(
        @NormalInterceptor interceptor: Interceptor,
        gson: Gson,
        adapterFactory: NetworkResponseAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(providesHttpClient(interceptor))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(adapterFactory)
            .build()
    }

    @Provides
    @Reusable
    fun provideAuthService(@NormalClient retrofit: Retrofit): AuthenticationService =
        retrofit.create(AuthenticationService::class.java)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NormalClient