package com.naulian.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    painter: Painter,
    text: String,
    modifier: Modifier = Modifier,
    itemSpacing: Dp = 8.dp,
    iconSize: Dp = 20.dp,
    iconTint: Color = Color.Unspecified,
    contentDescription: String? = null,
    style: TextStyle = TextStyle.Default,
    color: Color = MaterialTheme.colorScheme.onBackground,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(itemSpacing),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    IconText(
        painter = painter,
        text = buildAnnotatedString { append(text) },
        modifier = modifier,
        itemSpacing = itemSpacing,
        iconSize = iconSize,
        iconTint = iconTint,
        contentDescription = contentDescription,
        style = style,
        color = color,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    )
}

@Composable
fun IconText(
    painter: Painter,
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    itemSpacing: Dp = 8.dp,
    iconSize: Dp = 20.dp,
    iconTint: Color = Color.Unspecified,
    contentDescription: String? = null,
    style: TextStyle = TextStyle.Default,
    color: Color = MaterialTheme.colorScheme.onBackground,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(itemSpacing),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter,
            contentDescription = contentDescription,
            tint = iconTint
        )

        Text(text = text, style = style, color = color)
    }
}

@Preview
@Composable
private fun IconTextPreview() {
    Column(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        IconText(
            painter = rememberVectorPainter(Icons.Default.Info),
            text = "Information"
        )

        IconText(
            painter = rememberVectorPainter(Icons.Rounded.Email),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("username")
                }
                append("@codigo.co")
            }
        )
    }
}