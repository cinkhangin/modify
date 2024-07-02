package com.example.modify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.modify.ui.theme.ModifyTheme
import com.naulian.modify.DeleteDialog
import com.naulian.modify.button.Button
import com.naulian.modify.button.IconButton
import com.naulian.modify.button.OutlinedButton
import com.naulian.modify.sheet.BottomSheet
import com.naulian.modify.sheet.BottomSheetHeader
import com.naulian.modify.table.Table
import com.naulian.modify.themeColors
import com.naulian.modify.topbar.TopAppBar
import com.naulian.modify.web.Browser
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModifyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = themeColors.surface
                ) {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {

    var showDialog by remember {
        mutableStateOf(false)
    }

    var showBrowser by remember {
        mutableStateOf(false)
    }

    var isVisible by remember {
        mutableStateOf(false)
    }


    DeleteDialog(
        showDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmed = { showDialog = false }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TopAppBar(title = "Top App Bar")

        Button(onClick = { showDialog = true }) {
            Text(text = "Delete Dialog")
        }

        OutlinedButton(onClick = { isVisible = true }) {
            Text(text = "Bottom Sheet")
        }

        IconButton(
            imageVector = Icons.Rounded.Search,
            onClick = { showBrowser = true }
        )

        val data by remember {
            mutableStateOf(
                listOf(
                    listOf("Lorem", "Lorem", ""),
                    listOf("Lorem", "", "Lorem"),
                    listOf("Lorem", "Lorem", ""),
                    listOf("Lorem", "Lorem", "")
                )
            )
        }

        Table(modifier = Modifier.padding(12.dp), data = data) {
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = it)
        }
    }

    if (showBrowser) {
        Browser(
            url = "www.google.com",
            onUrlLoad = {},
            onLoading = {}
        )
    }

    val sheetState = rememberModalBottomSheetState(true)
    val coroutineScope = rememberCoroutineScope()

    BottomSheet(
        sheetState = sheetState,
        show = isVisible,
        onDismissRequest = { isVisible = false }
    ) {
        BottomSheetHeader(
            modifier = Modifier,
            title = "Bottom Sheet",
            onDismiss = {
                coroutineScope.launch { sheetState.hide() }
                    .invokeOnCompletion { isVisible = false }
            }
        )

        Text(text = "Sheet Content", modifier = Modifier.height(200.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModifyTheme {
        MainContent()
    }
}