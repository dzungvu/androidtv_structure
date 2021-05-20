package com.thedung.androidtvstructure.classes.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

@Suppress("PropertyName")
abstract class BaseActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycleScope.launchWhenStarted {
            initView()
            observeData()
        }
    }

    abstract fun initView()
    abstract suspend fun observeData()
}