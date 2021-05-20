package com.thedung.androidtvstructure.classes.bases

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.thedung.androidtvstructure.classes.interfaces.BaseView

@Suppress("PropertyName")
abstract class BaseFragment : Fragment(), BaseView {

    val TAG by lazy { javaClass.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            initViews()
            observeData()
        }
    }

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

    /**
     * Handle back pressed
     * @return false if want to block the back button, otherwise
     */
    open fun backHandler(): Boolean {
        return true
    }

    /**
     * Override this function if you know what are you doing !!!
     * @return true if the action won't block the back button, false otherwise
     */
    open fun onBackPressed(): Boolean {
        return when {
            !backHandler() -> false
            else -> true
        }
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    open fun onShowAgain() {

    }

    open fun onShowFromActivity() {

    }

    open fun onHideFromActivity() {

    }
}