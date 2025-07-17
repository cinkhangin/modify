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
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.PROPERTY_GETTER
import kotlin.annotation.AnnotationTarget.PROPERTY_SETTER
import kotlin.annotation.AnnotationTarget.TYPEALIAS
import kotlin.collections.listOf

@Target(
    CLASS,
    FUNCTION,
    PROPERTY,
    ANNOTATION_CLASS,
    CONSTRUCTOR,
    PROPERTY_SETTER,
    PROPERTY_GETTER,
    TYPEALIAS
)
annotation class DeprecatedIn(val value: String)

@Deprecated(message = "Use HugeIcons instead")
@DeprecatedIn("2025-07-01")
@Suppress("unused")
typealias MIcons = HugeIcons


object HugeIcons {
    val Account = R.drawable.ic_account
    val Add = R.drawable.ic_add

    val Back = R.drawable.ic_back

    val Close = R.drawable.ic_cancel
    val Cancel = Close
    val Comment = R.drawable.ic_comment
    val Copy = R.drawable.ic_copy

    val Delete = R.drawable.ic_delete
    val Dollar = R.drawable.ic_dollar
    val Done = R.drawable.ic_done
    val Draw = R.drawable.ic_draw

    val Edit = R.drawable.ic_pencil2

    val Favourite = R.drawable.ic_favourite
    val Folder = R.drawable.ic_folder

    val Home = R.drawable.ic_home

    val Image = R.drawable.ic_image
    val Internet = R.drawable.ic_internet

    val Link = R.drawable.ic_link
    val Location = R.drawable.ic_location
    val Lock = R.drawable.ic_lock

    val Menu = R.drawable.ic_menu
    val Message = R.drawable.ic_message

    val Notification = R.drawable.ic_notification

    val Pen = R.drawable.ic_edit2
    val Printer = R.drawable.ic_printer

    val Search = R.drawable.ic_search
    val Send = R.drawable.ic_sent
    val Settings = R.drawable.ic_settings
    val Share = R.drawable.ic_share
    val Star = R.drawable.ic_star
    val Shopping = R.drawable.ic_shopping

    val all by lazy {
        listOf(
            Copy,
            Back,
            Cancel,
            Delete,
            Dollar,
            Done,
            Settings,
            Search,
            Notification,
            Image,
            Add,
            Edit,
            Account,
            Comment,
            Draw,
            Favourite,
            Link,
            Location,
            Menu,
            Pen,
            Share,
            Send,
            Star,
            Message,
            Internet,
            Home,
            Shopping,
            Printer,
            Folder,
            Lock,
        )
    }
}

@Preview
@Composable
private fun HugeIconsPreview() {

    PreviewBox {
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
}