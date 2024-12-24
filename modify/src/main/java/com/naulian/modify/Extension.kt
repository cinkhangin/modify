package com.naulian.modify

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

fun <T> T.orIf(boolean: Boolean, other: () -> T): T {
    return if (boolean) other() else this
}

@Preview
@Composable
private fun IfPreview() {
    Preview {
        val color = "Red".orIf(false) { "Blue" }
        Text(text = color)
    }
}