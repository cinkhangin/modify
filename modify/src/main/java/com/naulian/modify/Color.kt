@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.Black")
)
@Stable
val Black = Color(0xFF000000)

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.DarkGray")
)
@Stable
val DarkGray = Color(0xFF444444)

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.Gray")
)
@Stable
val Gray = Color(0xFF888888)

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.LightGray")
)
@Stable
val LightGray = Color(0xFFCCCCCC)

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.White")
)
@Stable
val White = Color(0xFFFFFFFF)

@Deprecated(
    message = "Use Color Instead",
    replaceWith = ReplaceWith("androidx.compose.ui.graphics.Color.Transparent")
)
@Stable
val Transparent = Color(0x00000000)

fun colorFromHex(hexColor: String): Color {
    val legacyColor = hexColor.toColorInt()
    return Color(legacyColor)
}

fun Color.alpha(value: Float) = copy(alpha = value)
