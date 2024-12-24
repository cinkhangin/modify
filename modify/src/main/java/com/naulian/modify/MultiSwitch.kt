package com.naulian.modify

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MultiSwitchItem(
    val id: String = "",
    val name: String = "",
    @DrawableRes val icon: Int = 0,
)

@Composable
fun MultiSwitch(
    modifier: Modifier = Modifier,
    items: List<MultiSwitchItem>,
    currentIndex: Int = 0,
    thumbSize: Dp = 24.dp,
    spacing: Dp = 4.dp,
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    showName: Boolean = false,
    shape: RoundedCornerShape = RoundedCornerShape(50),
    thumbColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    normalIconColor: Color = MaterialTheme.colorScheme.onSurface,
    activeIconColor: Color = MaterialTheme.colorScheme.onPrimary,
    onCheckedChange: (Int) -> Unit
) {
    val localDensity = LocalDensity.current
    val textWidth = 74.dp
    val textWidthPx = remember { with(localDensity) { textWidth.toPx() } }
    val horPadPx = remember { with(localDensity) { horizontalItemPadding.toPx() } }
    val thumbSizePx = remember { with(localDensity) { thumbSize.toPx() } }
    val spacingPx = remember { with(localDensity) { spacing.toPx() } }

    val extraPadding by rememberDerivedState {
        if (showName) textWidthPx + horPadPx else 0f
    }

    val extraThumbSize by rememberDerivedState {
        if(showName) textWidth + horizontalItemPadding else 0.dp
    }

    val offset by animateFloatAsState(
        targetValue = (thumbSizePx + spacingPx + (horPadPx * 2) + extraPadding) * currentIndex,
        label = "ToggleOffset"
    )

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .padding(spacing)
            .height(thumbSize)
            .clip(shape)
    ) {

        Box(
            modifier = Modifier
                .size(width = thumbSize + extraThumbSize, height = thumbSize)
                .graphicsLayer {
                    translationX = offset
                }
                .background(color = thumbColor, shape = shape)
                .clip(shape),
        )

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(spacing),
        ) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = horizontalItemPadding,
                            vertical = verticalItemPadding
                        )
                        .noRippleClick { onCheckedChange(index) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        modifier = Modifier.padding(4.dp),
                        drawableId = item.icon,
                        iconSize = 16.dp,
                        tint = if (index == currentIndex) activeIconColor else normalIconColor
                    )

                    if (showName) {
                        HorizontalSpace(width = horizontalItemPadding)
                        Text(
                            modifier = Modifier.width(textWidth),
                            text = item.name.take(7),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (index == currentIndex) activeIconColor else normalIconColor
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ThemeSwitchPreview() {
    val items by rememberDataState(
        listOf(
            MultiSwitchItem(id = "1", name = "Post", icon = MIcons.Back),
            MultiSwitchItem(id = "2", name = "Home", icon = MIcons.Copy),
            MultiSwitchItem(id = "3", name = "Profile", icon = MIcons.Cancel)
        )
    )

    var currentIndex by rememberIntState()

    Preview {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column {
                MultiSwitch(
                    modifier = Modifier.padding(16.dp),
                    items = items,
                    spacing = 4.dp,
                    currentIndex = currentIndex,
                    onCheckedChange = {
                        currentIndex = it
                    }
                )

                MultiSwitch(
                    modifier = Modifier.padding(16.dp),
                    items = items,
                    spacing = 4.dp,
                    showName = true,
                    horizontalItemPadding = 4.dp,
                    currentIndex = currentIndex,
                    onCheckedChange = {
                        currentIndex = it
                    }
                )
            }
        }
    }
}