@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import android.graphics.Color as LegacyColor
import androidx.core.graphics.toColorInt

@Stable
val Black = Color(0xFF000000)

@Stable
val DarkGray = Color(0xFF444444)

@Stable
val Gray = Color(0xFF888888)

@Stable
val LightGray = Color(0xFFCCCCCC)

@Stable
val White = Color(0xFFFFFFFF)

@Stable
val Red = Color(0xFFFF0000)

@Stable
val Green = Color(0xFF00FF00)

@Stable
val Blue = Color(0xFF0000FF)

@Stable
val Yellow = Color(0xFFFFFF00)

@Stable
val Cyan = Color(0xFF00FFFF)

@Stable
val Magenta = Color(0xFFFF00FF)

@Stable
val Transparent = Color(0x00000000)

fun colorFromHex(hexColor: String): Color {
    val legacyColor = hexColor.toColorInt()
    return Color(legacyColor)
}

fun Color.alpha(value: Float) = copy(alpha = value)
