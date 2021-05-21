package com.thedung.androidtvstructure.classes.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {

    companion object {
        lateinit var INSTANCE: AppApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}