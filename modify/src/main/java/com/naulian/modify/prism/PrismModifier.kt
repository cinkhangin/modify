package com.naulian.modify.prism

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.modify.White


internal fun Modifier.prismStyle(): Modifier {
    return this then Modifier
        .dropShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 10.dp,
                color = White
            )
        )
        .background(PrismBackground, CircleShape)
        .innerShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 10.dp,
                color = PrismElectricCyan,
                offset = DpOffset(
                    x = 7.dp,
                    y = 7.dp
                )
            )
        )
        .innerShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 10.dp,
                color = PrismVibrantMagenta,
                offset = DpOffset(
                    x = (-7).dp,
                    y = 7.dp
                ),
                blendMode = BlendMode.Screen
            )
        )
        .innerShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 10.dp,
                color = PrismElectricCyan,
                offset = DpOffset(
                    x = (-7).dp,
                    y = (-7).dp
                )
            )
        )
        .innerShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 10.dp,
                color = PrismVibrantMagenta,
                offset = DpOffset(
                    x = 7.dp,
                    y = (-7).dp
                ),
                blendMode = BlendMode.Screen
            )
        )
        .innerShadow(
            shape = CircleShape,
            shadow = Shadow(
                radius = 2.dp,
                color = White,
            )
        )
}