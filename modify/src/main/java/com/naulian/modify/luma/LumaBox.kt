package com.naulian.modify.luma

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.modify.White

@Composable
internal fun LumaBox(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(5f / 3f)
            .background(White.copy(0.1f))
            .innerShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 50.dp,
                    color = LumaColors.CyberBlue.copy(0.8f),
                    offset = DpOffset(
                        x = 0.dp,
                        y = 50.dp
                    ),
                )
            )
            .innerShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 50.dp,
                    color = LumaColors.PlasmaPurple.copy(0.8f),
                    offset = DpOffset(
                        x = 0.dp,
                        y = (-50).dp
                    ),
                )
            )
            .innerShadow(
                shape = RectangleShape,
                shadow = Shadow(
                    radius = 5.dp,
                    color = White,
                    offset = DpOffset(
                        x = 0.dp,
                        y = 0.dp
                    ),
                )
            )

    )
}

@Composable
internal fun LumaCircuitCard(
    title: String,
    subtitle: String,
    accentColor: Color = Color(0xFF00CAFF) // CyberBlue
) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .width(300.dp)
            .height(180.dp)
    ) {
        // 1. The Glow Layer (Blurry background light)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 4.dp)
                .blur(20.dp)
                .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
        )

        // 2. The Glass Surface
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0F121A).copy(alpha = 0.9f), // Surface
                            Color(0xFF05070A).copy(alpha = 0.95f) // Background
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(accentColor.copy(alpha = 0.8f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            // 3. The "Circuit" Data Trail (Visual Accent)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 2.dp.toPx()
                // Top-right circuit line
                drawLine(
                    color = accentColor,
                    start = Offset(size.width - 40f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = accentColor,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, 40f),
                    strokeWidth = strokeWidth
                )
            }

            // 4. Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title.uppercase(),
                    color = accentColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    color = Color(0xFFF0F4FF), // PureLight
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview
@Composable
private fun LumaBoxPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LumaColors.Background
            )
            .padding(20.dp)
    ) {
        LumaCircuitCard(title = "Title", subtitle = "SubTitle")
    }
}