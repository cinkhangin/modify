package com.naulian.modify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberStringState(default: String = "") =
    remember { mutableStateOf(default) }

@Composable
fun rememberBooleanState(default: Boolean = false) =
    remember { mutableStateOf(default) }

@Composable
fun rememberLongState(default: Long = 0L) =
    remember { mutableLongStateOf(default) }

@Composable
fun rememberIntState(default: Int = 0) =
    remember { mutableIntStateOf(default) }

@Composable
fun rememberFloatState(default: Float = 0f) =
    remember { mutableFloatStateOf(default) }

@Composable
fun <T> rememberDataState(default: T) =
    remember { mutableStateOf(default) }

@Composable
fun <T> rememberListState(default: List<T>) =
    remember { mutableStateOf(default) }

//these are experimental
@Composable
fun <T> rememberMapState(default: Map<String, T>) =
    remember { mutableStateOf(default) }

@Composable
fun <T> rememberSetListState(default: Set<T>) =
    remember { mutableStateOf(default) }

@Composable
fun <T> rememberArrayState(default: Array<T>) =
    remember { mutableStateOf(default) }

@Composable
fun <T> rememberDerivedState(calculation: () -> T) =
    remember { derivedStateOf(calculation) }
