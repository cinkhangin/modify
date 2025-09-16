package com.naulian.modify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * ```kotlin
 * LazyColumn {
 *     columnItem {
 *         Text("Hello")
 *         Text("World")
 *     }
 * }
 * // output
 * Hello
 * World
 * ```
 * */
inline fun LazyListScope.columnItem(
    key: Any? = null,
    contentType: Any? = null,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    crossinline content: @Composable ColumnScope.() -> Unit,
) {
    item(key = key, contentType = contentType) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}

/**
 * ```kotlin
 * LazyColumn {
 *     rowItem {
 *         Text("Hello")
 *         Text("World")
 *     }
 * }
 * // output
 * HelloWorld
 * ```
 * */
inline fun LazyListScope.rowItem(
    key: Any? = null,
    contentType: Any? = null,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    crossinline content: @Composable RowScope.() -> Unit,
) {
    item(key = key, contentType = contentType) {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}

/**
 * ```kotlin
 * LazyColumn {
 *     boxItem(
 *         modifier = Modifier.fillMaxWidth(),
 *         contentAlignment = Alignment.TopEnd
 *     ) {
 *         Text("Hello World")
 *     }
 * }
 * // output
 *                                        Hello World
 * ```
 * */
inline fun LazyListScope.boxItem(
    key: Any? = null,
    contentType: Any? = null,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    crossinline content: @Composable BoxScope.() -> Unit,
) {
    item(key = key, contentType = contentType) {
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
        )
    }
}


inline fun <T> LazyListScope.itemsWithDivider(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    noinline divider: @Composable (() -> Unit) = {
        HorizontalDivider()
    },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    itemContent(items[it])

    if (it < items.lastIndex) {
        divider.invoke()
    }
}

@Preview
@Composable
private fun LazyColumnItemPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn {
            columnItem {
                Text("Hello")
                Text("World")
            }

            rowItem {
                Text("Hello")
                Text("World")
            }

            boxItem(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text("Hello World")
            }
        }
    }
}

@Preview
@Composable
private fun ItemsWithDividerPreview() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.padding(24.dp)) {
            itemsWithDivider(
                items = listOf(
                    "Lorem ipsum dolar asit amet",
                    "Lorem ipsum dolar asit amet",
                    "Lorem ipsum dolar asit amet",
                    "Lorem ipsum dolar asit amet",
                ),
                divider = {
                    HorizontalDottedLine(
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            ) {
                Text(text = it.uppercase())
            }
        }
    }
}