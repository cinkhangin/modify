package com.ckgin.modify.prism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ckgin.modify.Black
import com.ckgin.modify.Transparent
import com.ckgin.modify.White

@Composable
private fun ShadowBlending(blendMode: BlendMode) {
    Button(
        onClick = {},
        shape = CircleShape,
        modifier = Modifier
            .height(56.dp)
            .background(Color.Green, CircleShape)
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
                    blendMode = blendMode
                )
            )
            .innerShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 4.dp,
                    color = Color.White,
                    blendMode = BlendMode.Color
                )
            )
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(
            containerColor = Transparent
        ),
        content = {
            Text(blendMode.toString())
        }
    )
}

@Preview
@Composable
private fun ShadowBlendingPreview(modifier: Modifier = Modifier) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Black, shape = MaterialTheme.shapes.medium)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ShadowBlending(blendMode = BlendMode.Clear)
        ShadowBlending(blendMode = BlendMode.Src)
        ShadowBlending(blendMode = BlendMode.Dst)
        ShadowBlending(blendMode = BlendMode.SrcOver)

        ShadowBlending(blendMode = BlendMode.DstOver)
        ShadowBlending(blendMode = BlendMode.SrcIn)
        ShadowBlending(blendMode = BlendMode.DstIn)
        ShadowBlending(blendMode = BlendMode.SrcOut)

        ShadowBlending(blendMode = BlendMode.DstOut)
        ShadowBlending(blendMode = BlendMode.SrcAtop)
        ShadowBlending(blendMode = BlendMode.DstAtop)
        ShadowBlending(blendMode = BlendMode.Xor)

        ShadowBlending(blendMode = BlendMode.Plus)
        ShadowBlending(blendMode = BlendMode.Modulate)
        ShadowBlending(blendMode = BlendMode.Screen)
        ShadowBlending(blendMode = BlendMode.Overlay)

        ShadowBlending(blendMode = BlendMode.Darken)
        ShadowBlending(blendMode = BlendMode.Lighten)
        ShadowBlending(blendMode = BlendMode.ColorDodge)
        ShadowBlending(blendMode = BlendMode.ColorBurn)

        ShadowBlending(blendMode = BlendMode.Hardlight)
        ShadowBlending(blendMode = BlendMode.Softlight)
        ShadowBlending(blendMode = BlendMode.Difference)
        ShadowBlending(blendMode = BlendMode.Exclusion)

        ShadowBlending(blendMode = BlendMode.Multiply)
        ShadowBlending(blendMode = BlendMode.Hue)
        ShadowBlending(blendMode = BlendMode.Saturation)
        ShadowBlending(blendMode = BlendMode.Color)

        ShadowBlending(blendMode = BlendMode.Luminosity)
    }
}