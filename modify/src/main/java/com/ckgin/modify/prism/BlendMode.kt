package com.ckgin.modify.prism

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ckgin.modify.White

@Composable
private fun ObjectBlending(blendMode: BlendMode) {

    Canvas(
        modifier = Modifier
            .height(50.dp)
            .width(75.dp)
    ) {
        val center = size.height / 2

        drawCircle(
            color = androidx.compose.ui.graphics.Color.Red,
            center = Offset(x = center, y = center),
            radius = center,
        )
        drawCircle(
            center = Offset(x = size.height, y = center),
            color = androidx.compose.ui.graphics.Color.Blue,
            blendMode = blendMode,
            radius = center,
        )
    }
}

@Preview
@Composable
private fun ObjectBlendingPreview(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White, shape = MaterialTheme.shapes.medium)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ObjectBlending(blendMode = BlendMode.Clear)
        ObjectBlending(blendMode = BlendMode.Src)
        ObjectBlending(blendMode = BlendMode.Dst)
        ObjectBlending(blendMode = BlendMode.SrcOver)

        ObjectBlending(blendMode = BlendMode.DstOver)
        ObjectBlending(blendMode = BlendMode.SrcIn)
        ObjectBlending(blendMode = BlendMode.DstIn)
        ObjectBlending(blendMode = BlendMode.SrcOut)

        ObjectBlending(blendMode = BlendMode.DstOut)
        ObjectBlending(blendMode = BlendMode.SrcAtop)
        ObjectBlending(blendMode = BlendMode.DstAtop)
        ObjectBlending(blendMode = BlendMode.Xor)

        ObjectBlending(blendMode = BlendMode.Plus)
        ObjectBlending(blendMode = BlendMode.Modulate)
        ObjectBlending(blendMode = BlendMode.Screen)
        ObjectBlending(blendMode = BlendMode.Overlay)

        ObjectBlending(blendMode = BlendMode.Darken)
        ObjectBlending(blendMode = BlendMode.Lighten)
        ObjectBlending(blendMode = BlendMode.ColorDodge)
        ObjectBlending(blendMode = BlendMode.ColorBurn)

        ObjectBlending(blendMode = BlendMode.Hardlight)
        ObjectBlending(blendMode = BlendMode.Softlight)
        ObjectBlending(blendMode = BlendMode.Difference)
        ObjectBlending(blendMode = BlendMode.Exclusion)

        ObjectBlending(blendMode = BlendMode.Multiply)
        ObjectBlending(blendMode = BlendMode.Hue)
        ObjectBlending(blendMode = BlendMode.Saturation)
        ObjectBlending(blendMode = BlendMode.Color)

        ObjectBlending(blendMode = BlendMode.Luminosity)
    }
}