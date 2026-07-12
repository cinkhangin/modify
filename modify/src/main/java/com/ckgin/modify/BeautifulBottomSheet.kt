package com.ckgin.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeautifulBottomSheet(
    show: Boolean,
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded)
    ),
    enabledGestures: Boolean = true,
    shape: Shape = RoundedCornerShape(24.dp),
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    content: @Composable ColumnScope.() -> Unit
) {

    var openBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(show) {
        if (show) openBottomSheet = true
        else launch { sheetState.hide() }
            .invokeOnCompletion { openBottomSheet = false }
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            shape = RectangleShape,
            sheetGesturesEnabled = enabledGestures,
            containerColor = Color.Transparent,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            scrimColor = scrimColor,
            dragHandle = null,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(containerColor, shape)
                        .clip(shape)
                ) {
                    content()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun BottomSheetPreview() {
    PreviewScreen {
        BeautifulBottomSheet(
            show = true,
            sheetState = rememberBottomSheetState(
                initialValue = SheetValue.Expanded
            ),
            onDismissRequest = {},
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Bottom Sheet Content")
                Button(onClick = {}) {
                    Text("Toggle Split Screen")
                }
            }
        }
    }
}
