package com.thedung.androidtvstructure.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.thedung.androidtvstructure.R
import com.thedung.androidtvstructure.classes.extension.launchActivityWithClear
import com.thedung.androidtvstructure.ui.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            launchActivityWithClear<HomeActivity> { }
        }, 1_500L)
    }
}