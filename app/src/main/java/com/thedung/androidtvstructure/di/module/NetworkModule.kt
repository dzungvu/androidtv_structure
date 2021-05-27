package com.thedung.androidtvstructure.di.module

import com.thedung.androidtvstructure.BuildConfig

class NetworkModule {

    private external fun getBaseUrl(debug: Boolean): String

    private fun getBaseUrl(): String {
        return getBaseUrl(BuildConfig.DEBUG)
    }
}