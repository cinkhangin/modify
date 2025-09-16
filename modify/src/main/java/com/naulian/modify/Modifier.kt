@file:Suppress("unused")

package com.naulian.modify

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
    return if (condition) this then Modifier.modifier() else this
}

@Preview
@Composable
private fun IfTest() {
    Box(modifier = Modifier
        .size(100.dp)
        .background(Color.Blue)
        .If(true) {
            padding(10.dp).background(Color.Red)
        }
        .padding(10.dp)
        .background(Color.Green))
}


fun Modifier.touchIndicator(scaleTo: Float = 1.2f, action: (Boolean) -> Unit = {}) = composed {
    var targetScale by remember { mutableFloatStateOf(1f) }
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    scale(scale)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    val change = event.changes.first()
                    targetScale = if (change.pressed) scaleTo else 1f
                    action(change.pressed)
                }
            }
        }
}


@Preview
@Composable
private fun TouchIndicatorPreview() {
    MaterialTheme {
        Box(modifier = Modifier.Companion
            .background(White)
            .padding(20.dp)) {
            var isTouch by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .touchIndicator { isTouch = it }
                    .border(width = 1.dp, color = Black, shape = CircleShape)
                    .background(White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (isTouch) "Touch" else "Not Touch")
            }
        }
    }
}


