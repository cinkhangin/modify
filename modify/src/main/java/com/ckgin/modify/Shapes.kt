package com.ckgin.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.min
import androidx.compose.ui.graphics.asComposePath
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath

@Immutable
data class SquircleShape(
    val topStart: CornerSize,
    val topEnd: CornerSize,
    val bottomEnd: CornerSize,
    val bottomStart: CornerSize,
    val smoothing: Float = DefaultSmoothing
) : Shape {

    constructor(
        cornerRadiusFraction: Float = DefaultCornerFraction,
        smoothing: Float = DefaultSmoothing
    ) : this(
        corner = FractionCornerSize(cornerRadiusFraction),
        smoothing = smoothing
    )

    constructor(
        corner: CornerSize,
        smoothing: Float = DefaultSmoothing
    ) : this(
        topStart = corner,
        topEnd = corner,
        bottomEnd = corner,
        bottomStart = corner,
        smoothing = smoothing
    )

    constructor(
        cornerPercent: Int,
        smoothing: Float = DefaultSmoothing
    ) : this(
        corner = CornerSize(cornerPercent),
        smoothing = smoothing
    )

    init {
        require(smoothing.isFinite() && smoothing in 0f..1f) {
            "Smoothing must be between 0 and 1."
        }
    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        require(size.width.isFinite() && size.height.isFinite()) {
            "Shape dimensions must be finite."
        }

        if (size.width <= 0f || size.height <= 0f) {
            return Outline.Generic(Path())
        }

        val startTop = topStart.resolve(size, density)
        val endTop = topEnd.resolve(size, density)
        val endBottom = bottomEnd.resolve(size, density)
        val startBottom = bottomStart.resolve(size, density)

        // Match the clockwise vertex order: TL, TR, BR, BL.
        val radii = when (layoutDirection) {
            LayoutDirection.Ltr -> listOf(
                startTop,
                endTop,
                endBottom,
                startBottom
            )

            LayoutDirection.Rtl -> listOf(
                endTop,
                startTop,
                startBottom,
                endBottom
            )
        }

        // Construct at the actual dimensions to avoid stretching corners.
        // RoundedPolygon fits rounding and smoothing to available edges.
        val polygon = RoundedPolygon(
            vertices = floatArrayOf(
                0f, 0f,
                size.width, 0f,
                size.width, size.height,
                0f, size.height
            ),
            perVertexRounding = radii.map { radius ->
                CornerRounding(
                    radius = radius,
                    smoothing = smoothing
                )
            },
            centerX = size.width / 2f,
            centerY = size.height / 2f
        )

        return Outline.Generic(polygon.toPath().asComposePath())
    }

    private fun CornerSize.resolve(
        size: Size,
        density: Density
    ): Float {
        val radius = toPx(size, density)

        require(radius.isFinite() && radius >= 0f) {
            "Corner sizes must resolve to finite, non-negative values."
        }

        return radius
    }

    private companion object {
        const val DefaultCornerFraction = 0.2f
        const val DefaultSmoothing = 0.6f
    }
}

/** Creates a squircle with the same [size] in dp on all four corners. */
fun SquircleShape(
    size: Dp,
    smoothing: Float = 0.6f
): SquircleShape = SquircleShape(CornerSize(size), smoothing)

/**
 * Creates a squircle with independently sized or grouped dp corners.
 *
 * A specific corner takes precedence over a grouped value. At an intersection, [top] or [bottom]
 * takes precedence over [left] or [right]. Left and right correspond to logical start and end, so
 * they are mirrored in right-to-left layouts.
 */
fun SquircleShape(
    topStart: Dp? = null,
    topEnd: Dp? = null,
    bottomEnd: Dp? = null,
    bottomStart: Dp? = null,
    left: Dp? = null,
    right: Dp? = null,
    top: Dp? = null,
    bottom: Dp? = null,
    smoothing: Float = 0.6f
): SquircleShape = SquircleShape(
    topStart = CornerSize(topStart ?: top ?: left ?: 0.dp),
    topEnd = CornerSize(topEnd ?: top ?: right ?: 0.dp),
    bottomEnd = CornerSize(bottomEnd ?: bottom ?: right ?: 0.dp),
    bottomStart = CornerSize(bottomStart ?: bottom ?: left ?: 0.dp),
    smoothing = smoothing
)

/**
 * Creates a squircle with independently sized or grouped percentage corners.
 *
 * Grouped percentages follow the same precedence and layout-direction behavior as the dp overload.
 */
fun SquircleShape(
    topStartPercent: Int? = null,
    topEndPercent: Int? = null,
    bottomEndPercent: Int? = null,
    bottomStartPercent: Int? = null,
    leftPercent: Int? = null,
    rightPercent: Int? = null,
    topPercent: Int? = null,
    bottomPercent: Int? = null,
    smoothing: Float = 0.6f
): SquircleShape = SquircleShape(
    topStart = CornerSize(topStartPercent ?: topPercent ?: leftPercent ?: 0),
    topEnd = CornerSize(topEndPercent ?: topPercent ?: rightPercent ?: 0),
    bottomEnd = CornerSize(bottomEndPercent ?: bottomPercent ?: rightPercent ?: 0),
    bottomStart = CornerSize(bottomStartPercent ?: bottomPercent ?: leftPercent ?: 0),
    smoothing = smoothing
)

private fun Path.addTopRightCorner(width: Float, radius: Float, controlFactor: Float) {
    if (radius == 0f) return
    val control = radius * controlFactor
    cubicTo(
        width - radius + control,
        0f,
        width,
        radius - control,
        width,
        radius
    )
}

private fun Path.addBottomRightCorner(
    width: Float,
    height: Float,
    radius: Float,
    controlFactor: Float
) {
    if (radius == 0f) return
    val control = radius * controlFactor
    cubicTo(
        width,
        height - radius + control,
        width - radius + control,
        height,
        width - radius,
        height
    )
}

private fun Path.addBottomLeftCorner(height: Float, radius: Float, controlFactor: Float) {
    if (radius == 0f) return
    val control = radius * controlFactor
    cubicTo(
        radius - control,
        height,
        0f,
        height - radius + control,
        0f,
        height - radius
    )
}

private fun Path.addTopLeftCorner(radius: Float, controlFactor: Float) {
    if (radius == 0f) return
    val control = radius * controlFactor
    cubicTo(
        0f,
        radius - control,
        radius - control,
        0f,
        radius,
        0f
    )
}

private data class CornerRadii(
    val topLeft: Float,
    val topRight: Float,
    val bottomRight: Float,
    val bottomLeft: Float
) {
    fun scaledToFit(width: Float, height: Float): CornerRadii {
        val scale = minOf(
            1f,
            scaleFor(width, topLeft + topRight),
            scaleFor(width, bottomLeft + bottomRight),
            scaleFor(height, topLeft + bottomLeft),
            scaleFor(height, topRight + bottomRight)
        )

        return if (scale == 1f) {
            this
        } else {
            CornerRadii(
                topLeft * scale,
                topRight * scale,
                bottomRight * scale,
                bottomLeft * scale
            )
        }
    }

    private fun scaleFor(side: Float, cornerSum: Float): Float {
        return if (cornerSum > side) side / cornerSum else 1f
    }
}

@Immutable
private data class FractionCornerSize(private val fraction: Float) : CornerSize {
    init {
        require(fraction in 0f..0.5f) {
            "Corner radius fraction must be between 0 and 0.5."
        }
    }

    override fun toPx(shapeSize: Size, density: Density): Float {
        return min(shapeSize.width, shapeSize.height) * fraction
    }

    override fun toString(): String = "CornerSize(fraction=$fraction)"
}

@Preview
@Composable
private fun SquircleExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SquirclePreviewBox(SquircleShape(smoothing = 0.7f))
            SquirclePreviewBox(SquircleShape(cornerPercent = 35))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SquirclePreviewBox(
                SquircleShape(
                    left = 18.dp,
                    right = 6.dp
                )
            )
            SquirclePreviewBox(
                SquircleShape(
                    topPercent = 50,
                    bottomPercent = 10,
                    smoothing = 0.8f
                )
            )
        }
    }
}

@Preview(name = "20% Corner Comparison", showBackground = true)
@Composable
private fun CornerShapeComparison() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CornerShapePreviewItem(
            label = "Rounded 30%",
            shape = RoundedCornerShape(30)
        )
        CornerShapePreviewItem(
            label = "Squircle 30%",
            shape = SquircleShape(cornerPercent = 30)
        )
    }
}

@Composable
private fun CornerShapePreviewItem(label: String, shape: Shape) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SquirclePreviewBox(shape)
        Text(text = label)
    }
}

@Composable
private fun SquirclePreviewBox(shape: Shape) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(shape)
            .background(Color(0xFF007AFF))
    )
}
