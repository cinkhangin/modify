package com.naulian.modify.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.PreviewScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    show: Boolean = true,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(true),
    dragHandle: (@Composable () -> Unit)? = null,
    bgColor: Color = Color(0xFFFFFFFF),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    shape: RoundedCornerShape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            },
            sheetState = sheetState,
            shape = shape,
            containerColor = bgColor,
            content = {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = verticalArrangement,
                    horizontalAlignment = horizontalAlignment
                ) {
                    content()
                }
            },
            dragHandle = dragHandle
        )
    }
}

//preview not working
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BottomSheetPreview() {
    PreviewScreen(color = Color.Black) {
        val showSheet by remember {
            mutableStateOf(true)
        }

        BottomSheet(
            show = showSheet,
            bgColor = Color.Blue,
            onDismiss = {}
        ) {

        }
    }
}