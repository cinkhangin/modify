@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
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
