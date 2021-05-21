package com.thedung.androidtvstructure.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.thedung.androidtvstructure.classes.bases.BaseActivity
import com.thedung.androidtvstructure.classes.delegates.viewBinding
import com.thedung.androidtvstructure.classes.extension.launchActivityWithClear
import com.thedung.androidtvstructure.databinding.ActivitySplashScreenBinding
import com.thedung.androidtvstructure.ui.home.HomeActivity

class SplashScreenActivity : BaseActivity() {

    private val binding by viewBinding(ActivitySplashScreenBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            launchActivityWithClear<HomeActivity> { }
        }, 1_500L)
    }

    override fun initViews() {

    }

    override suspend fun observeData() {

    }
}