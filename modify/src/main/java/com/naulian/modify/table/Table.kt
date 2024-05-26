package com.naulian.modify.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.Preview
import com.naulian.modify.bold
import com.naulian.modify.leftBorder
import com.naulian.modify.normal
import com.naulian.modify.rightBorder

@Composable
fun Table(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {

    Column(modifier) {
        header()

        Box(
            Modifier
                .fillMaxWidth()
                .border(
                    1.dp, MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .background(
                    Color.White,
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                ),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

@Composable
fun TableHeader(modifier: Modifier = Modifier, title: String) {
    Box(
        modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
        )
    }
}

@Composable
fun TableHeader(modifier: Modifier = Modifier, items: List<String>) {
    Row(
        modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            TableCell(
                text = item,
                modifier = Modifier
                    .weight(1f)
                    .run {
                        if (index == 0) this
                        else this.rightBorder()
                    },
                textColor = MaterialTheme.colorScheme.onPrimary,
                textStyle = MaterialTheme.typography.bodyLarge.bold()
            )
        }
    }
}

@Composable
fun <T> Table(
    modifier: Modifier = Modifier,
    items: List<T>,
    rowItem: @Composable ColumnScope.(T) -> Unit
) {
    Column(modifier) {
        items.dropLast(1).forEach {
            rowItem(it)
            HorizontalDivider()
        }
        rowItem(items.last())
    }
}

@Composable
fun TableItems(items: List<List<String>>, emphasis: Int = Emphasis.NONE) {
    Column(Modifier.fillMaxWidth()) {
        for (i in 0 until items.size - 1) {
            TableRow(items = items[i], emphasis)
            HorizontalDivider()
        }

        TableRow(items = items.last(), emphasis)
    }
}

object Emphasis {
    const val FIRST = 0
    const val NONE = 1000
    const val ALL = 1001
}

@Composable
fun TableRow(items: List<String>, emphasis: Int = Emphasis.NONE) {
    val typography = MaterialTheme.typography.bodyLarge
    val boldStyle = typography.bold()
    val normalStyle = typography.normal()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            TableCell(
                text = item,
                modifier = Modifier
                    .weight(1f)
                    .run {
                        if (index == 0) this
                        else this.rightBorder()
                    },
                textStyle = when (emphasis) {
                    Emphasis.ALL -> boldStyle
                    Emphasis.NONE -> normalStyle
                    else -> if (emphasis == index) boldStyle else normalStyle
                }
            )
        }
    }
}

@Composable
fun <T> TableRow(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemContent: @Composable RowScope.(Modifier, T) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until items.size - 1) {
            itemContent(
                Modifier
                    .leftBorder()
                    .weight(1f),
                items[i]
            )
        }
        itemContent(Modifier.weight(1f), items.last())
    }
}

@Composable
fun TableCell(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    Box(
        modifier = modifier
            .height(40.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
        )
    }
}

@Preview
@Composable
fun TablePreview() {
    val items = listOf(
        listOf("Order", "Below $80", "$80 and above"),
        listOf("Fees", "$10", "$2.95")
    )
    Preview {
        Table(
            modifier = Modifier.padding(20.dp),
            header = {
                TableHeader(items = items.first())
            },
            content = {
                TableItems(items = items, emphasis = Emphasis.FIRST)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LazyTablePreview() {
    val items = listOf(
        listOf("Fish4dogs", "Truline", ""),
        listOf("Burp!", "", "Black Hawk"),
        listOf("Pronature", "Canine Caviar", ""),
        listOf("1st Choice", "Vets All Natural", "")
    )
    Preview {
        Table(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                ),
            items = items,
            rowItem = {
                TableRow(items = it) { modifier, item ->
                    TableCell(modifier = modifier, text = item)
                }
            }
        )
    }
}