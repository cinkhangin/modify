@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.ui.graphics.Color
import android.graphics.Color as LegacyColor

fun colorFromHex(hexColor: String): Color {
    val legacyColor = LegacyColor.parseColor(hexColor)
    return Color(legacyColor)
}

fun Color.alpha(value: Float) = copy(alpha = value)
