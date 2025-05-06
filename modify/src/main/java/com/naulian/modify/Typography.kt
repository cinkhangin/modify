@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

object Fonts {
    val JetBrainsMono = FontFamily(Font(R.font.jetbrains_mono))
}

val TextStyle.Thin get() = copy(fontWeight = FontWeight.Thin)
val TextStyle.ExtraLight get() = copy(fontWeight = FontWeight.ExtraLight)
val TextStyle.Light get() = copy(fontWeight = FontWeight.Light)
val TextStyle.Normal get() = copy(fontWeight = FontWeight.Normal)
val TextStyle.Medium get() = copy(fontWeight = FontWeight.Medium)
val TextStyle.SemiBold get() = copy(fontWeight = FontWeight.SemiBold)
val TextStyle.Bold get() = copy(fontWeight = FontWeight.Bold)
val TextStyle.ExtraBold get() = copy(fontWeight = FontWeight.ExtraBold)
val TextStyle.Black get() = copy(fontWeight = FontWeight.Black)

@Preview
@Composable
private fun FontWeightPreview() {
    PreviewColumn {
        val textStyle = MaterialTheme.typography.bodyLarge
        Text(text = "Thin", style = textStyle.Thin)
        Text(text = "Extra Light", style = textStyle.ExtraLight)
        Text(text = "Light", style = textStyle.Light)
        Text(text = "Normal", style = textStyle.Normal)
        Text(text = "Medium", style = textStyle.Medium)
        Text(text = "Semi Bold", style = textStyle.SemiBold)
        Text(text = "Bold", style = textStyle.Bold)
        Text(text = "Extra Bold", style = textStyle.ExtraBold)
        Text(text = "Black", style = textStyle.Black)
    }
}