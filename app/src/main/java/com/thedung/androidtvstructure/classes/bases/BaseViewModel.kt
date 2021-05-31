package com.thedung.androidtvstructure.classes.bases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

abstract class BaseViewModel: ViewModel() {
    /**
     * Run a task in background
     * @return a Deferred to stop the task manually
     */
    protected fun async(error: (throwable: Throwable) -> Unit = {}, call: suspend () -> Unit = {}): Deferred<*> {
        return viewModelScope.async(Dispatchers.Default) {
            runCatching {
                call()
            }.onFailure {
                it.printStackTrace()
                error(it)
            }
        }
    }
}