package com.naulian.modify

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StateCache<T>(private val defaultValue: T) {

    private val _dataFlow = MutableStateFlow(defaultValue)
    val dataFlow = _dataFlow.asStateFlow()

    val data get() = dataFlow.value

    fun update(value: T) {
        _dataFlow.update { value }
    }

    fun reset() {
        update(defaultValue)
    }
}