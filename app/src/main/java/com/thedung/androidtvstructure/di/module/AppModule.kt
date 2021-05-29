package com.thedung.androidtvstructure.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thedung.androidtvstructure.classes.application.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.thedung.androidtvstructure.utils.GlideApp
import com.thedung.androidtvstructure.utils.GlideRequests

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGSon(): Gson = GsonBuilder().disableHtmlEscaping().create()

    @Provides
    @Singleton
    fun provideAppConfig(@ApplicationContext context: Context): AppConfig = AppConfig(context)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context


    @Provides
    @Singleton
    fun providesGsonConvertFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun providesGlideRequests(@ApplicationContext context: Context): GlideRequests =
        GlideApp.with(context)
}