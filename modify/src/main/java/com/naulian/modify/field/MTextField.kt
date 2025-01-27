package com.naulian.modify.field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.If
import com.naulian.modify.PreviewScreen
import com.naulian.modify.rememberBooleanState

@Composable
fun MBasicTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeHolder: String = "Text here",
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    var onFocus by rememberBooleanState()

    BasicTextField(
        modifier = modifier
            .onFocusChanged { onFocus = it.hasFocus },
        state = state,
        decorator = {
            if (state.text.isEmpty() && !onFocus) {
                Text(text = placeHolder, style = textStyle)
            } else it()
        },
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        inputTransformation = inputTransformation,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction
    )
}


@Composable
fun MTextField(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    accentColor : Color = MaterialTheme.colorScheme.primary,
    shape: Shape = MaterialTheme.shapes.small,
    state: TextFieldState,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeHolder: String = "Text here",
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    var onFocus by rememberBooleanState()

    BasicTextField(
        modifier = modifier
            .onFocusChanged { onFocus = it.hasFocus },
        state = state,
        decorator = {
            Box(
                modifier = Modifier
                    .heightIn(min = 58.dp)
                    .background(color = containerColor, shape = shape)
                    .If(onFocus) {
                        border(1.dp, accentColor, shape = shape)
                    }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (state.text.isEmpty()) {
                    Text(text = placeHolder, style = textStyle)
                } else it()
            }
        },
        enabled = enabled,
        readOnly = readOnly,
        inputTransformation = inputTransformation,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction
    )
}

@Preview
@Composable
private fun MeoTextFieldPreview() {
    PreviewScreen {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MBasicTextField(state = TextFieldState(""))
            MTextField(state = TextFieldState("hello"))
        }
    }
}