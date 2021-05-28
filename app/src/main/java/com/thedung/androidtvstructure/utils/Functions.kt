package com.thedung.androidtvstructure.utils

import com.thedung.androidtvstructure.BuildConfig

inline fun debug(task: () -> Unit) {
    if (BuildConfig.DEBUG)
        task()
}