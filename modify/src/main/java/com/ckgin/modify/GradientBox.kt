package com.ckgin.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun VerticalGradientBox(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(brush = Brush.verticalGradient(colors = colors)),
        content = content
    )
}

@Composable
fun VerticalGradientColumn(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(brush = Brush.verticalGradient(colors = colors)),
        content = content,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    )
}

@Composable
fun VerticalGradientRow(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(brush = Brush.verticalGradient(colors = colors)),
        content = content,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    )
}