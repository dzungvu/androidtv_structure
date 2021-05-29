package com.thedung.androidtvstructure.classes.bases

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.thedung.androidtvstructure.classes.interfaces.BaseLeanbackView

abstract class BaseLeanbackFragment : Fragment(), BaseLeanbackView {

    @Suppress("PropertyName")
    val TAG by lazy { javaClass.simpleName }

    /**
     * Transfer new data into target fragment
     * @param bundle data to put in fragment
     * @param notify true if want to notify new arguments | mean trigger [onNewArgs]
     */
    fun setArgs(bundle: Bundle?, notify: Boolean = true) {
        arguments = bundle
        if (notify)
            onNewArgs(arguments)
    }

    /**
     * Handle new args data from other class
     * @param args new data set by [setArgs]
     */
    protected open fun onNewArgs(args: Bundle?) {}
}