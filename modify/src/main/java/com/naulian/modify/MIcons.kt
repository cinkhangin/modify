package com.naulian.modify

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//MIcons icons are from hugeicons.com
object MIcons {
    val Copy = R.drawable.ic_copy
    val Back = R.drawable.ic_back
    val Cancel = R.drawable.ic_cancel
    val Delete = R.drawable.ic_delete
    val Settings = R.drawable.ic_settings
    val Search = R.drawable.ic_search
    val Notification = R.drawable.ic_notification
    val Image = R.drawable.ic_image
    val Add = R.drawable.ic_add

    val Edit = R.drawable.ic_pencil2
    val Edit1 = R.drawable.ic_edit1
    val Edit2 = R.drawable.ic_edit2
    val Edit3 = R.drawable.ic_pencil1
}

@Preview
@Composable
private fun MIconsPreview() {

    val iconList = listOf(
        MIcons.Copy,
        MIcons.Back,
        MIcons.Cancel,
        MIcons.Delete,
        MIcons.Settings,
        MIcons.Search,
        MIcons.Notification,
        MIcons.Image,
        MIcons.Add,
        MIcons.Edit,
        MIcons.Edit1,
        MIcons.Edit2,
        MIcons.Edit3
    )
    PreviewBox {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(48.dp)
        ) {
            items(iconList) {
                MIcon(
                    modifier = Modifier.padding(12.dp),
                    drawableId = it
                )
            }
        }
    }
}