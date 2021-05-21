package com.thedung.androidtvstructure.classes.delegates

import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> FragmentActivity.viewBinding(initializer: (LayoutInflater) -> T) =
    ActivityViewBindingDelegate(this, initializer)