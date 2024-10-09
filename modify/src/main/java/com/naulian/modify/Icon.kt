@file:Suppress("unused")

package com.naulian.modify

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Icon as M3Icon

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableId: Int,
    contentDescription: String = "Icon"
) {
    M3Icon(
        modifier = modifier,
        painter = painterResource(drawableId),
        contentDescription = contentDescription
    )
}