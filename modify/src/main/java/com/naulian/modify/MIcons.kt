package com.naulian.modify

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//MIcons icons are from hugeicons.com
object MIcons {
    val Account = R.drawable.ic_account
    val Add = R.drawable.ic_add

    val Back = R.drawable.ic_back

    val Cancel = R.drawable.ic_cancel
    val Comment = R.drawable.ic_comment
    val Copy = R.drawable.ic_copy

    val Delete = R.drawable.ic_delete
    val Draw = R.drawable.ic_draw

    val Edit = R.drawable.ic_pencil2

    val Favourite = R.drawable.ic_favourite

    val Home = R.drawable.ic_home

    val Image = R.drawable.ic_image
    val Internet = R.drawable.ic_internet

    val Link = R.drawable.ic_link
    val Location = R.drawable.ic_location

    val Menu = R.drawable.ic_menu
    val Message = R.drawable.ic_message

    val Notification = R.drawable.ic_notification

    val Pen = R.drawable.ic_edit2

    val Search = R.drawable.ic_search
    val Settings = R.drawable.ic_settings
    val Share = R.drawable.ic_share
    val Star = R.drawable.ic_star
    val Shopping = R.drawable.ic_shopping
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
        MIcons.Account,
        MIcons.Comment,
        MIcons.Draw,
        MIcons.Favourite,
        MIcons.Link,
        MIcons.Location,
        MIcons.Menu,
        MIcons.Pen,
        MIcons.Share,
        MIcons.Star,
        MIcons.Message,
        MIcons.Internet,
        MIcons.Home,
        MIcons.Shopping,
    )
    PreviewBox {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(48.dp)
        ) {
            items(iconList) {
                Icon(
                    painter = painterResource(it),
                    modifier = Modifier.padding(12.dp),
                    contentDescription = null
                )
            }
        }
    }
}