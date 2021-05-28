package com.thedung.androidtvstructure.classes.application

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.thedung.androidtvstructure.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppConfig @Inject constructor(context: Context) {

    companion object {
        private const val SP_PREFIX = BuildConfig.APPLICATION_ID
        private const val APP_FIRST_ACTIVE = "${SP_PREFIX}_FIRST_ACTIVE"

        //Application
        private const val APP_VERSION_CODE = "${SP_PREFIX}_APP_VERSION_CODE"
        private const val URL = "URL"
        private const val LANGUAGE = "LANGUAGE"
        const val LANGUAGE_EN = "en"
        const val LANGUAGE_VN = "vi"

        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    var isFirstActive: Boolean
        get() = sharedPreferences.getBoolean(APP_FIRST_ACTIVE, false)
        set(value) = sharedPreferences.edit { putBoolean(APP_FIRST_ACTIVE, value) }

    var token: String
        get() = sharedPreferences.getString(TOKEN, "").orEmpty()
        set(value) = sharedPreferences.edit { putString(TOKEN, value) }

    var refreshToken: String
        get() = sharedPreferences.getString(REFRESH_TOKEN, "").orEmpty()
        set(value) = sharedPreferences.edit { putString(REFRESH_TOKEN, value) }
}