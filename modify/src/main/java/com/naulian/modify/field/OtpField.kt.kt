package com.naulian.modify.field

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.Preview

@Composable
fun OtpField(
    modifier: Modifier = Modifier,
    state : TextFieldState,
    error: Boolean = false,
    inputCount: Int = 6,
    focusOnStart : Boolean = true,
    focusRequester: FocusRequester = FocusRequester(),
    style: TextStyle = MaterialTheme.typography.titleLarge,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    containerColor: Color = MaterialTheme.colorScheme.background,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {

    var inFocus by remember { mutableStateOf(false) }

    SideEffect {
        if(focusOnStart){
            focusRequester.requestFocus()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            state = state,
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    inFocus = focusState.hasFocus
                },
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = InputTransformation {
                if (length > inputCount) originalText.subSequence(0, inputCount) else originalText
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorator = {}
        )

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            repeat(inputCount) { index ->
                CharField(
                    modifier = Modifier.weight(1f),
                    index = index,
                    text = state.text,
                    error = error,
                    style = style,
                    inFocus = inFocus,
                    textColor = textColor,
                    containerColor = containerColor,
                    accentColor = accentColor,
                    shape = shape
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
private fun CharField(
    modifier: Modifier = Modifier,
    index: Int,
    text: CharSequence,
    style: TextStyle,
    error: Boolean,
    inFocus: Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    containerColor: Color = MaterialTheme.colorScheme.background,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.small,
) {
    val isFocused = text.length == index && inFocus
    val char = text.getOrElse(index) { Char.MIN_VALUE }.toString()

    val borderColor = when {
        isFocused -> accentColor
        error -> MaterialTheme.colorScheme.error
        else -> containerColor
    }

    Box(
        modifier = modifier
            .height(56.dp)
            .background(color = containerColor, shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .clip(RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = char,
            style = style,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun OtpFieldPreview() {
    Preview {
        OtpField(state = TextFieldState("123456"))
    }
}