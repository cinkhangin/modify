package com.naulian.modify.prism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

internal val PrismElectricCyan = Color(0xFF00F2FE)
internal val PrismBackground = Color(0xFF0B0E14)
internal val PrismVibrantMagenta = Color(0xFFFB00FF)
internal val PrismDeepViolet = Color(0xFF6200EA)
internal val PrismSoftLavender = Color(0xFFD1C4E9)

@Composable
private fun Circle(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .clip(shape = CircleShape),
        content = content
    )
}

@Composable
internal fun Circle(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .background(color = color, shape = CircleShape)
            .clip(shape = CircleShape)
    )
}

@Preview
@Composable
private fun ColorPreview() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = PrismBackground, shape = MaterialTheme.shapes.medium)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Circle(
            modifier = Modifier.size(48.dp),
            color = PrismElectricCyan
        )
        Circle(
            modifier = Modifier.size(48.dp),
            color = PrismVibrantMagenta
        )
        Circle(
            modifier = Modifier.size(48.dp),
            color = PrismDeepViolet
        )
        Circle(
            modifier = Modifier.size(48.dp),
            color = PrismSoftLavender
        )
        Circle(
            modifier = Modifier.size(48.dp),
            color = Color.White
        )
    }
}