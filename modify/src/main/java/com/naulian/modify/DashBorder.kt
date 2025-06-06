package com.naulian.modify

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSimple
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.dashedBorder(
    border: BorderStroke,
    shape: Shape = RectangleShape,
    on: Dp = 4.dp,
    off: Dp = 4.dp
) = dashedBorder(width = border.width, brush = border.brush, shape = shape, on, off)

fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    shape: Shape = RectangleShape,
    on: Dp = 4.dp,
    off: Dp = 4.dp
) = dashedBorder(width, SolidColor(color), shape, on, off)

fun Modifier.dashedBorder(
    width: Dp,
    brush: Brush,
    shape: Shape,
    on: Dp = 4.dp,
    off: Dp = 4.dp
): Modifier = drawWithCache {
    val dashPathEffect = PathEffect.dashPathEffect(
        floatArrayOf(on.toPx(), off.toPx())
    )
    val outline: Outline = shape.createOutline(size, layoutDirection, this)

    val borderSize = if (width == Dp.Hairline) 1f else width.toPx()

    var insetOutline: Outline? = null
    var stroke = Stroke(borderSize, pathEffect = dashPathEffect)

    var pathClip: Path? = null
    var inset = 0f

    var insetPath: Path? = null

    val shouldDrawBorder = borderSize > 0 && size.minDimension > 0f

    if (shouldDrawBorder && outline !is Outline.Rectangle) {
        val strokeWidth = 1.2f * borderSize //1.2f why?
        inset = borderSize - strokeWidth / 2
        val insetSize = Size(
            size.width - inset * 2,
            size.height - inset * 2
        )
        insetOutline = shape.createOutline(insetSize, layoutDirection, this)
        stroke = Stroke(strokeWidth, pathEffect = dashPathEffect)

        pathClip = when (outline) {
            is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
            is Outline.Generic -> outline.path
            else -> null
        }

        insetPath = when {
            insetOutline is Outline.Rounded && !insetOutline.roundRect.isSimple -> {
                Path().apply {
                    addRoundRect(insetOutline.roundRect)
                    translate(Offset(inset, inset))
                }
            }

            insetOutline is Outline.Generic -> {
                Path().apply { addPath(insetOutline.path, Offset(inset, inset)) }
            }

            else -> null
        }
    }

    val shouldDrawRoundedBorder = insetOutline != null && pathClip != null

    onDrawWithContent {
        drawContent()

        if (shouldDrawBorder) {
            if (shouldDrawRoundedBorder) {
                val isSimpleRoundRect =
                    insetOutline is Outline.Rounded && insetOutline.roundRect.isSimple

                withTransform({
                    clipPath(pathClip)
                    if (isSimpleRoundRect) {
                        translate(inset, inset)
                    }
                }) {
                    when {
                        isSimpleRoundRect -> {
                            val rRect = insetOutline.roundRect
                            drawRoundRect(
                                brush = brush,
                                topLeft = Offset(rRect.left, rRect.top),
                                size = Size(rRect.width, rRect.height),
                                cornerRadius = rRect.topLeftCornerRadius,
                                style = stroke
                            )
                        }

                        insetPath != null -> {
                            drawPath(insetPath, brush, style = stroke)
                        }
                    }
                }
            } else {
                // Rectangular border fast path
                val strokeWidth = stroke.width
                val halfStrokeWidth = strokeWidth / 2
                drawRect(
                    brush = brush,
                    topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
                    size = Size(
                        size.width - strokeWidth,
                        size.height - strokeWidth
                    ),
                    style = stroke
                )
            }
        }
    }
}


@Preview
@Composable
private fun DashBorderPreview() {
    PreviewRow(
        modifier = Modifier
            .background(White)
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {


        Box(
            modifier = Modifier
                .size(100.dp)
                .background(White)
                .dashedBorder(
                    width = 4.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFF44336),
                            Color(0xFF00BCD4)
                        )
                    ),
                    shape = RectangleShape,
                    on = 8.dp,
                    off = 8.dp
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(White)
                .dashedBorder(
                    border = BorderStroke(width = 4.dp, color = Black),
                    shape = CircleShape,
                    on = 8.dp,
                    off = 8.dp
                )
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(White)
                .dashedBorder(
                    width = 4.dp,
                    color = Black,
                    shape = RoundedCornerShape(20.dp),
                    on = 8.dp,
                    off = 8.dp
                )
        )
    }
}