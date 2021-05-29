package com.thedung.androidtvstructure.classes.extension

import android.animation.Animator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Toast message from View (in customView ...)
 */
internal fun View.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

/**
 * Fast way to set view visibility
 */
fun View.visible() {
    post { visibility = View.VISIBLE }
}

fun View.gone() {
    post { visibility = View.GONE }
}

fun View.invisible() {
    post { visibility = View.INVISIBLE }
}

fun <T : View> T.onClick(delayBetweenClick: Long = 0, block: T.() -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClick = 0L

        override fun onClick(v: View?) {
            when {
                delayBetweenClick <= 0 -> { block() }
                System.currentTimeMillis() - lastClick > delayBetweenClick -> {
                    lastClick = System.currentTimeMillis()
                    block()
                }
                else -> {}
            }
        }
    })
}

fun <T: View> T.onClickDelay(block: T.() -> Unit) {
    onClick(50, block)
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.fadeIn(duration: Long = 200, listener: (()->Unit) ? = null) {
    post {
        alpha = 0f
        visible()
        animate().alpha(1f).setDuration(duration).setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                listener?.invoke()
            }
        }).start()
    }
}

fun View.fadeOut(duration: Long = 200, goneView: Boolean = true, listener: (()->Unit) ? = null) {
    post {
        animate().alpha(0f).setDuration(duration).setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                listener?.invoke()
            }
        }).start()
    }
}