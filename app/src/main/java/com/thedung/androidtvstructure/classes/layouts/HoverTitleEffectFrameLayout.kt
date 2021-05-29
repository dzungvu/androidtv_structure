package com.thedung.androidtvstructure.classes.layouts

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import com.thedung.androidtvstructure.R

/**
 * Avoid using height wrap_content
 */
class HoverTitleEffectFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var expandHeight = 0
    var shrinkHeight = 0
    var animationDuration = 80L
    var visibleFlag = -1

    private val valueAnimator = ValueAnimator().apply {
        duration = animationDuration
        addUpdateListener {
            updateLayoutParams { height = (it.animatedValue as Int) }
        }
    }

    init {
        attrs?.run {
            val typeArray = context.obtainStyledAttributes(this, R.styleable.HoverTitleEffectFrameLayout, 0, 0)
            expandHeight = typeArray.getDimension(R.styleable.HoverTitleEffectFrameLayout_expandHeight, 0f).toInt()
            shrinkHeight = typeArray.getDimension(R.styleable.HoverTitleEffectFrameLayout_shrinkHeight, 0f).toInt()
            animationDuration = typeArray.getInt(R.styleable.HoverTitleEffectFrameLayout_animDuration, 80).toLong()
            typeArray.recycle()
        }
    }

    override fun setVisibility(visibility: Int) {
        if (visibleFlag == visibility) return
        else visibleFlag = visibility

        when (visibility) {
            View.GONE -> hide()
            View.VISIBLE -> show()
            else -> {}
        }
    }

    fun show() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
            valueAnimator.duration = ((1 - height.toFloat() / expandHeight) * animationDuration).toLong()
            valueAnimator.setIntValues(height, expandHeight)
        } else {
            valueAnimator.duration = animationDuration
            valueAnimator.setIntValues(shrinkHeight, expandHeight)
        }
        valueAnimator.start()
    }

    fun hide() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
            valueAnimator.duration = ((height.toFloat() / expandHeight) * animationDuration).toLong()
            valueAnimator.setIntValues(height, shrinkHeight)
        } else {
            valueAnimator.duration = animationDuration
            valueAnimator.setIntValues(expandHeight, shrinkHeight)
        }
        valueAnimator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        valueAnimator.cancel()
    }
}