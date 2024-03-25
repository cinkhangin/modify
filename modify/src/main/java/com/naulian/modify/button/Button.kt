package com.naulian.modify.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.PreviewColumn
import androidx.compose.material3.Button as M3Button
import androidx.compose.material3.OutlinedButton as M3OutlineButton

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    M3Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
private fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    border: BorderStroke = ButtonDefaults.outlinedButtonBorder,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    M3OutlineButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        enabled = enabled,
        shape = shape,
        border = border,
        contentPadding = contentPadding,
        content = content,
    )
}

@Preview
@Composable
private fun ButtonPreview() {
    PreviewColumn {
        Button(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), onClick = {}
        ) {
            Text(text = "Button")
        }

        OutlinedButton(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(), onClick = {}
        ) {
            Text(text = "Button")
        }
    }
}