package com.thedung.androidtvstructure.ui.home

import android.os.Bundle
import com.thedung.androidtvstructure.classes.bases.BaseActivity
import com.thedung.androidtvstructure.classes.delegates.viewBinding
import com.thedung.androidtvstructure.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initViews() {

    }

    override suspend fun observeData() {

    }
}