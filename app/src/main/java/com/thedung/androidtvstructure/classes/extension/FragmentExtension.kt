package com.thedung.androidtvstructure.classes.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * Quick inflate view in fragment onCreate function
 */
internal fun Fragment.inflateView(viewId: Int, viewGroup: ViewGroup? = null, attachToRoot: Boolean = false) : View {
    return layoutInflater.inflate(viewId, viewGroup, attachToRoot)
}

/**
 * Launch activity
 */
inline fun <reified ACTIVITY : Activity> Fragment.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    sendArgument: Boolean = false,
    noinline init: Intent.() -> Unit = {}
) {
    context?.run {
        val intent = newIntent<ACTIVITY>(this)
        if (sendArgument)
            arguments?.let { intent.putExtras(it) }
        intent.init()
        startActivityForResult(intent, requestCode, options)
    }
}

fun Fragment.onActivityForResult(callback: ActivityResultCallback<ActivityResult>): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult(), callback)
}

/**
 * Toast message in Fragment
 */
internal fun Fragment.toast(message: String, durationMs: Int = Toast.LENGTH_SHORT) {
    activity?.toast(message, durationMs)
}

internal fun Fragment.toast(messageResId: Int, durationMs: Int = Toast.LENGTH_SHORT) {
    activity?.toast(messageResId, durationMs)
}

internal fun Fragment.getDimensInt(resId: Int): Int {
    return requireContext().resources.getDimensionPixelSize(resId)
}

internal fun Fragment.getDimensFloat(resId: Int): Float {
    return requireContext().resources.getDimension(resId)
}

