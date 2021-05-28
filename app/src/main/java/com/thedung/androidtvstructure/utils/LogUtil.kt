package com.thedung.androidtvstructure.utils

import android.util.Log

/**
 * Use for
 * Created by DzungVu on 5/28/2021.
 */
@Suppress("unused")
object LogUtil {
    fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }
}