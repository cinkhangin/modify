package com.ckgin.modify

import androidx.compose.runtime.Composable

@Composable
fun IfNotEmpty(str: String, block: @Composable (String) -> Unit) {
    if (str.isNotEmpty()) block(str)
}

@Composable
fun <T> IfNotEmpty(list: List<T>, block: @Composable (List<T>) -> Unit) {
    if (list.isNotEmpty()) block(list)
}