package com.ckgin.modify.prism

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun PrismButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
            .height(56.dp)
            .prismStyle(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Transparent
        ),
        interactionSource = interactionSource,
        content = content
    )
}

@Preview
@Composable
private fun PrismButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = PrismBackground, shape = MaterialTheme.shapes.medium)
            .padding(12.dp),
    ) {
        PrismButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = "Button")
        }
    }
}

