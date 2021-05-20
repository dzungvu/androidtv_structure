package com.thedung.androidtvstructure.utils

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object LocaleUtils {

    /**
     * Change app language. Must call everytime an activity start.
     * @Note: Not recommend. App should have the same config as system.
     */
    fun changeAppLanguage(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}