package com.thedung.androidtvstructure.classes.delegates

import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val ref: FragmentActivity,
    private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<FragmentActivity, T>, LifecycleObserver {

    private var binding: T? = null

    init {
        ref.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (binding == null)
            binding = initializer(ref.layoutInflater)
        with(ref) {
            setContentView(binding!!.root)
            lifecycle.removeObserver(this@ActivityViewBindingDelegate)
        }
    }

    override fun getValue(thisRef: FragmentActivity, property: KProperty<*>): T {
        if (binding == null) {
            if (Looper.myLooper() != Looper.getMainLooper())
                throw IllegalThreadStateException("Call on main thread please")
            binding = initializer(thisRef.layoutInflater)
        }
        return binding!!
    }

}