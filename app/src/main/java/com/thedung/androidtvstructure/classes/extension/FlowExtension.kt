package com.thedung.androidtvstructure.classes.extension

import com.thedung.androidtvstructure.classes.network_adapter.models.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
fun <T : Any> apiFlow(@BuilderInference block: suspend FlowCollector<Resource<T>>.() -> Unit) =
    flow(block)
        .onStart { emit(Resource.Loading()) }
        .catch {
            it.printStackTrace()
            emit(Resource.Error())
        }.flowOn(Dispatchers.IO)