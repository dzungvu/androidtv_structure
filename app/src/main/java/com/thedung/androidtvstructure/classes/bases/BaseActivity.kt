package com.thedung.androidtvstructure.classes.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thedung.androidtvstructure.classes.interfaces.BaseView

@Suppress("PropertyName")
abstract class BaseActivity : AppCompatActivity(), BaseView {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycleScope.launchWhenStarted {
            initViews()
            observeData()
        }
    }
}