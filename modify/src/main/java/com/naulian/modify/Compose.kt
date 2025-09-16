package com.naulian.modify

import androidx.compose.runtime.Composable

@Composable
fun IfNotEmpty(string: String, block: @Composable (String) -> Unit) {
    if (string.isNotEmpty()) block(string)
}