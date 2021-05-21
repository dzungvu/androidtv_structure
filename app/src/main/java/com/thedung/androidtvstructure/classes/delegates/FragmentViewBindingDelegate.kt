package com.thedung.androidtvstructure.classes.delegates

import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val ref: Fragment,
    private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<Fragment, T>, LifecycleObserver {

    private var binding: T? = null

    init {
        ref.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (binding == null)
            binding = initializer(ref.layoutInflater)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        ref.lifecycle.removeObserver(this)
        binding = null
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (binding == null) {
            if (Looper.myLooper() != Looper.getMainLooper())
                throw IllegalThreadStateException("Call on main thread please")
            binding = initializer(thisRef.layoutInflater)
        }
        return binding!!
    }

}