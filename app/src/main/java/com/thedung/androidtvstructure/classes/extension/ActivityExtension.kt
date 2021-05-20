package com.thedung.androidtvstructure.classes.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

inline fun <reified ACTIVITY : Activity> newIntent(context: Context): Intent =
    Intent(context, ACTIVITY::class.java)

inline fun <reified ACTIVITY : Activity> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<ACTIVITY>(this)
    intent.init()
    intent.data = this.intent.data
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified ACTIVITY : Activity> ActivityResultLauncher<Intent>.launchActivity(
    currentActivity: Activity,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<ACTIVITY>(currentActivity)
    intent.init()
    intent.data = currentActivity.intent.data
    launch(intent)
}

inline fun <reified ACTIVITY : Activity> Context.launchActivityWithClear(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    newIntent<ACTIVITY>(this).run {
        flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        init()
        startActivity(this, options)
    }
}

fun ComponentActivity.onActivityForResult(callback: ActivityResultCallback<ActivityResult>): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)
}

/**
 * Toast message in Activity
 * @param durationMs time in millisecond or [Toast.LENGTH_SHORT] [Toast.LENGTH_LONG] in case want to hide toast sooner than default
 */
internal fun Activity.toast(message: String, durationMs: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, durationMs)
    toast.show()
    if (durationMs > 1) {
        Handler(Looper.getMainLooper()).postDelayed({
            toast.cancel()
        }, durationMs.toLong())
    }
}

internal fun Activity.toast(messageResId: Int, durationMs: Int = Toast.LENGTH_SHORT) {
    toast(getString(messageResId), durationMs)
}

internal fun Activity.getDimensInt(resId: Int): Int {
    return applicationContext.resources.getDimensionPixelSize(resId)
}

internal fun Activity.getDimensFloat(resId: Int): Float {
    return applicationContext.resources.getDimension(resId)
}

internal fun Activity.drawable(id: Int): Drawable? {
    return ContextCompat.getDrawable(applicationContext, id)
}