@file:Suppress("unused")

package com.naulian.modify

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.leftBorder(strokeWidth: Dp = 1.dp, color: Color = Color.LightGray) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        drawBehind {
            val width = size.width - strokeWidthPx / 2
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = width, y = 0f),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.rightBorder(strokeWidth: Dp = 1.dp, color: Color = Color.LightGray) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        drawBehind {
            val xOffSet = strokeWidthPx / 2
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = xOffSet, y = 0f),
                end = Offset(x = xOffSet, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.noRippleClick(
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit,
) = this.clickable(
    enabled = enabled,
    interactionSource = interactionSource,
    indication = null,
    onClick = onClick
)

val TextFieldState.string get() = text.toString()

@SuppressLint("ComposableNaming")
@Composable
fun Modifier.If(condition: Boolean, modifier: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) this.then(this.modifier()) else this
}

@Preview
@Composable
private fun IfTest() {
    Box(modifier = Modifier
        .size(100.dp)
        .background(Color.Blue)
        .If(false) {
            padding(20.dp).background(Color.Red)
        }
        .padding(10.dp)
        .background(Color.Green))
}

@Composable
fun IfNotEmpty(string: String, block: @Composable (String) -> Unit) {
    if (string.isNotEmpty()) block(string)
}
