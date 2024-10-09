@file:Suppress("unused")

package com.naulian.modify

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val TextStyle.Normal get() = copy(fontWeight = FontWeight.Normal)
val TextStyle.Bold get() = copy(fontWeight = FontWeight.Bold)
