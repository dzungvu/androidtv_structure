package com.thedung.androidtvstructure.classes.layouts

import android.content.Context
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.*


/**
 * Saves the focused grandchild position.
 * Helps add persistent focus feature to various ViewGroups.
 * @hide
 */
internal class PersistentFocusWrapper : FrameLayout {
    private var mSelectedPosition = -1
    var savedChild: View? = null
        private set

    /**
     * By default, focus is persisted when searching vertically
     * but not horizontally.
     */
    private var mPersistFocusVertical = true

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    val grandChildCount: Int
        get() {
            val wrapper = getChildAt(0) as ViewGroup
            return wrapper.childCount
        }

    /**
     * Clears the selected position and clears focus.
     */
    fun clearSelection() {
        mSelectedPosition = -1
        if (hasFocus()) {
            clearFocus()
        }
    }

    /**
     * Persist focus when focus search direction is up or down.
     */
    fun persistFocusVertical() {
        mPersistFocusVertical = true
    }

    /**
     * Persist focus when focus search direction is left or right.
     */
    fun persistFocusHorizontal() {
        mPersistFocusVertical = false
    }

    private fun shouldPersistFocusFromDirection(direction: Int): Boolean {
        return mPersistFocusVertical && (direction == View.FOCUS_UP || direction == View.FOCUS_DOWN) ||
                !mPersistFocusVertical && (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT)
    }

    override fun addFocusables(views: ArrayList<View>, direction: Int, focusableMode: Int) {
        if (DEBUG) Log.v(TAG, "addFocusables")
        if (hasFocus() || grandChildCount == 0 ||
            !shouldPersistFocusFromDirection(direction)
        ) {
            super.addFocusables(views, direction, focusableMode)
        } else {
            // Select a child in requestFocus
            views.add(this)
        }
    }

    override fun requestChildFocus(child: View, focused: View?) {
        super.requestChildFocus(child, focused)
        var view = focused
        while (view != null && view.parent !== child) {
            view = view.parent as View
        }
        mSelectedPosition = if (view == null) -1 else (child as ViewGroup).indexOfChild(view)
        savedChild = view
        if (DEBUG) Log.v(
            TAG,
            "requestChildFocus focused $focused mSelectedPosition $mSelectedPosition"
        )
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect): Boolean {
        if (DEBUG) Log.v(
            TAG,
            "requestFocus mSelectedPosition $mSelectedPosition"
        )
        val wrapper = if (getChildAt(0) is ViewGroup) getChildAt(0) as ViewGroup else null
        if (wrapper != null && mSelectedPosition >= 0 && mSelectedPosition < grandChildCount) {
            if (savedChild != null) {
                savedChild!!.requestFocus(direction, previouslyFocusedRect)
                return true
            } else if (wrapper.getChildAt(mSelectedPosition)
                    .requestFocus(direction, previouslyFocusedRect)
            ) {
                return true
            }
        }
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    internal class SavedState : BaseSavedState {
        var mSelectedPosition = 0

        constructor(`in`: Parcel) : super(`in`) {
            mSelectedPosition = `in`.readInt()
        }

        constructor(superState: Parcelable?) : super(superState)

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(mSelectedPosition)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState?> =
                object : Parcelable.Creator<SavedState?> {
                    override fun createFromParcel(`in`: Parcel): SavedState? {
                        return SavedState(`in`)
                    }

                    override fun newArray(size: Int): Array<SavedState?> {
                        return arrayOfNulls(size)
                    }
                }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        if (DEBUG) Log.v(TAG, "onSaveInstanceState")
        val savedState =
            SavedState(super.onSaveInstanceState())
        savedState.mSelectedPosition = mSelectedPosition
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val savedState =
            state as SavedState
        mSelectedPosition = state.mSelectedPosition
        if (DEBUG) Log.v(TAG, "onRestoreInstanceState mSelectedPosition $mSelectedPosition")
        super.onRestoreInstanceState(savedState.superState)
    }

    companion object {
        private const val TAG = "PersistentFocusWrapper"
        private const val DEBUG = false
    }
}