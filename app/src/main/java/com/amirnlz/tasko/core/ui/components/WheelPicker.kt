package com.amirnlz.tasko.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun <T> WheelPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    // The initially selected index
    initialIndex: Int = 0,
    // Invoked whenever the center (selected) item changes due to user scroll or initial snap
    onSelected: (index: Int, item: T) -> Unit = { _, _ -> },
    // Number of items you want visible above/below the center
    visibleCount: Int = 5,
    // Height of each item in dp (so we can center them precisely)
    itemHeight: Int = 40,
    // If true, smoothly animate scroll to initialIndex the first time
    animateInitialScroll: Boolean = true
) {
    val lazyListState = rememberLazyListState()

    // Once the layout is done, we animate scroll to initialIndex (if needed)
    LaunchedEffect(key1 = items, key2 = initialIndex) {
        if (animateInitialScroll) {
            lazyListState.animateScrollToItem(initialIndex)
        } else {
            lazyListState.scrollToItem(initialIndex)
        }
    }

    // Determine the currently selected item by the middle of the viewport
    val centerItemIndex by remember {
        derivedStateOf {
            // The item in the vertical center is approximately:
            // firstVisibleItemIndex + (number of items that fit in half the screen?).
            // But we can do a simpler approach by comparing which item is "closest" to the center offset.
            val layoutInfo = lazyListState.layoutInfo
            val viewportCenter =
                layoutInfo.viewportStartOffset + (layoutInfo.viewportSize / 2).height
            val closest = layoutInfo.visibleItemsInfo.minByOrNull { info ->
                val itemCenter = info.offset + (info.size / 2)
                (itemCenter - viewportCenter).absoluteValue
            }
            closest?.index ?: 0
        }
    }

    // Notify external listener whenever the center item changes
    LaunchedEffect(centerItemIndex) {
        if (centerItemIndex in items.indices) {
            onSelected(centerItemIndex, items[centerItemIndex])
        }
    }

    // The total height to show. We'll show visibleCount + center item, but overall
    // you can tweak the parent container size to your liking
    val totalVisibleItems = visibleCount.coerceAtLeast(1)
    val wheelHeightDp = (totalVisibleItems * itemHeight).dp

    Box(
        modifier = modifier
            .height(wheelHeightDp)
            // Center horizontally, clip so items don't extend beyond
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Marker for the center item (optional highlight line)
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
        )

        // The vertical list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true
        ) {
            itemsIndexed(items) { index, item ->
                // We measure how far this item is from the center item
                val distanceFromCenter = (index - centerItemIndex).absoluteValue.toFloat()
                // The further from center, the more we scale and fade out
                val scaleFactor = lerp(
                    start = 0.7f,
                    stop = 1.0f,
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )
                val alphaFactor = lerp(
                    start = 0.3f,
                    stop = 1.0f,
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )

                // Each item: a 40.dp height row
                Box(
                    modifier = Modifier
                        .height(itemHeight.dp)
                        .fillMaxWidth()
                        .scale(scaleFactor)
                        .alpha(alphaFactor),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        text = item.toString(),
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}
