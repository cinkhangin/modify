@file:Suppress("unused")

package com.naulian.modify

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon

@Composable
fun MIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableId: Int,
    contentDescription: String = "Icon"
) {
    Icon(
        modifier = modifier,
        painter = painterResource(drawableId),
        contentDescription = contentDescription
    )
}

@Composable
fun DrawableIcon(
    modifier: Modifier = Modifier,
    drawableId: Int,
    iconSize: Dp = 20.dp,
    tint: Color = MaterialTheme.colorScheme.onBackground
) {
    Icon(
        modifier = modifier.size(iconSize),
        painter = painterResource(id = drawableId),
        contentDescription = null,
        tint = tint
    )
}

@Composable
fun VectorIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    iconSize: Dp = 20.dp,
    tint: Color = Color.Unspecified
) {
    Icon(
        modifier = modifier.size(iconSize),
        imageVector = imageVector,
        contentDescription = null,
        tint = tint
    )
}