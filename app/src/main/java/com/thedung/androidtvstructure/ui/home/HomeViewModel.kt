package com.thedung.androidtvstructure.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thedung.androidtvstructure.classes.bases.BaseViewModel
import com.thedung.androidtvstructure.data.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val _homeEntity = MutableLiveData<Any>()
    val homeEntity = Transformations.switchMap(_homeEntity) {
        repository.getHome().asLiveData(viewModelScope.coroutineContext)
    }

    fun fetchHome() {
        _homeEntity.postValue("")
    }
}
