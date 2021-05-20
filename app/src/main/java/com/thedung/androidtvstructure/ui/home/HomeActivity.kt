package com.thedung.androidtvstructure.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thedung.androidtvstructure.R
import com.thedung.androidtvstructure.classes.bases.BaseActivity

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun initView() {

    }

    override suspend fun observeData() {

    }
}