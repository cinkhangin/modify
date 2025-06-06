package com.naulian.modify

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDottedLine(
    modifier: Modifier = Modifier,
    color: Color = DividerDefaults.color,
    thickness: Dp = DividerDefaults.Thickness,
    dotLength: Float = 4f,
    dotSpacing: Float = 6f
) {
    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(dotLength, dotSpacing),
        phase = 0f
    )

    Box(modifier = modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .width(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(thickness.toPx() / 2, 0f),
                end = Offset(thickness.toPx() / 2, size.height),
                strokeWidth = thickness.toPx(),
                pathEffect = pathEffect
            )
        }
    }
}

@Composable
fun HorizontalDottedLine(
    modifier: Modifier = Modifier,
    color: Color = DividerDefaults.color,
    thickness: Dp = DividerDefaults.Thickness,
    dotLength: Float = 4f,
    dotSpacing: Float = 6f
) {
    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(dotLength, dotSpacing),
        phase = 0f
    )

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(thickness)
        ) {
            drawLine(
                color = color,
                start = Offset(0f, thickness.toPx() / 2),
                end = Offset(size.width, thickness.toPx() / 2),
                strokeWidth = thickness.toPx(),
                pathEffect = pathEffect
            )
        }
    }
}

@Preview
@Composable
private fun DottedLinePreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            HorizontalDottedLine()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VerticalDottedLine(modifier = Modifier.height(100.dp))
                VerticalDottedLine(modifier = Modifier.height(100.dp))
            }
            HorizontalDottedLine()
        }
    }
}