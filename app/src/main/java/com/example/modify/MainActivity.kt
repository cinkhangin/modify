package com.example.modify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.modify.ui.theme.ModifyTheme
import com.naulian.modify.HugeIcons
import com.naulian.modify.field.MTextField
import com.naulian.modify.table.MTable
import com.naulian.modify.themeColors
import com.naulian.modify.web.MBrowser
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
    var showBrowser by remember {
        mutableStateOf(false)
    }

    val textFieldState = remember {
        TextFieldState("")
    }
    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(Unit) {
        delay(10000)
        focusRequester.requestFocus()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

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

        MTable(modifier = Modifier.padding(12.dp), data = data) {
            Text(modifier = Modifier.padding(horizontal = 10.dp), text = it)
        }

        MTextField(
            modifier = Modifier.fillMaxWidth(),
            state = textFieldState,
            focusRequester = focusRequester
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(48.dp)
        ) {
            items(items = HugeIcons.all) {
                Icon(
                    painter = painterResource(it),
                    modifier = Modifier.padding(12.dp),
                    contentDescription = null
                )
            }
        }
    }

    if (showBrowser) {
        MBrowser(
            url = "www.google.com",
            onUrlLoad = {},
            onLoading = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ModifyTheme {
        MainContent()
    }
}