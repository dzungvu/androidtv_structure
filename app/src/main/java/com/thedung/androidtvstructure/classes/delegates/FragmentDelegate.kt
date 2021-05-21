package com.thedung.androidtvstructure.classes.delegates

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> Fragment.viewBinding(initializer: (LayoutInflater) -> T) =
    FragmentViewBindingDelegate(this, initializer)