package com.naulian.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun GradientTest() {
    FlowRow(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    )
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    ),
                    start = Offset(x = 0f, y = 0f),
                    end = Offset(x = 0f, y = Float.POSITIVE_INFINITY),
                    tileMode = TileMode.Mirror
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    ),
                    tileMode = TileMode.Decal
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    ),
                    tileMode = TileMode.Repeated
                ))
        )

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    )
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    )
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    )
                ))
        )
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(brush = Brush.sweepGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta
                    )
                ))
        )
    }
}