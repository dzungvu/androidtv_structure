package com.thedung.androidtvstructure.classes.interfaces

interface BaseView {

    /**
     * Init and set views event
     */
    fun initViews()

    /**
     * Observe ViewModel data
     */
    suspend fun observeData() {}

}