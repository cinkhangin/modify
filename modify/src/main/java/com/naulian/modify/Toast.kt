package com.naulian.modify

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

data class ToastMessage(
    val text: String,
    val time: Long = System.currentTimeMillis()
) {
    val expired get() = time + ToastDuration.LONG < System.currentTimeMillis()
}

fun String.asToastMessage() = ToastMessage(this)

object ToastDuration {
    const val SHORT = 2500L
    const val LONG = 4000L
}

@ExperimentalModifyApi
@Composable
fun Toast(
    message: ToastMessage,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopCenter,
    duration: Long = ToastDuration.SHORT,
    content: @Composable BoxScope.(String) -> Unit
) {

    var text by remember { mutableStateOf(message.text) }

    LaunchedEffect(message) {
        if (!message.expired) {
            text = message.text
            delay(duration)
            text = ""
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = contentAlignment) {
        AnimatedVisibility(
            visible = text.isNotEmpty(),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            content(text)
        }
    }
}