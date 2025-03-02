package com.amirnlz.tasko.core.ui.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.absoluteValue

@Composable
fun <T> WheelPicker(
    items: List<T>,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    onSelected: (index: Int, item: T) -> Unit = { _, _ -> },
    visibleCount: Int = 5,
    itemHeight: Int = 40,
    animateInitialScroll: Boolean = true
) {
    // State management for the scroll position
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

    // Handle empty state case first
    if (items.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("No items available")
        }
        return
    }

    // Ensure initial index stays within valid bounds
    val safeInitialIndex = initialIndex.coerceIn(0, items.lastIndex)

    // Initial scroll positioning logic
    LaunchedEffect(items, safeInitialIndex) {
        // Wait for layout measurement to complete
        // Essential for accurate scroll calculations
        while (lazyListState.layoutInfo.viewportSize.height == 0) {
            delay(16) // Wait for 1 frame (60fps = ~16ms/frame)
        }

        // Calculate pixel dimensions
        val itemHeightPx = with(density) { itemHeight.dp.roundToPx() }
        // Determine center offset to properly align items
        val centerOffset = (lazyListState.layoutInfo.viewportSize.height / 2) - (itemHeightPx / 2)

        // Scroll to initial position with animation option
        if (animateInitialScroll) {
            lazyListState.animateScrollToItem(
                index = safeInitialIndex,
                scrollOffset = -centerOffset // Negative offset centers the item
            )
        } else {
            lazyListState.scrollToItem(
                index = safeInitialIndex,
                scrollOffset = -centerOffset
            )
        }
    }

    // Track centered item using derived state for performance
    val centerItemIndex by remember {
        derivedStateOf {
            // Calculate viewport center point
            val viewportCenter = lazyListState.layoutInfo.viewportStartOffset +
                    (lazyListState.layoutInfo.viewportSize.height / 2)

            // Find closest item to center using layout information
            lazyListState.layoutInfo.visibleItemsInfo
                .minByOrNull { item ->
                    // Calculate distance from item center to viewport center
                    abs(item.offset + (item.size / 2) - viewportCenter)
                }?.index ?: safeInitialIndex // Fallback to safe index
        }
    }

    // Notify parent component about selection changes
    LaunchedEffect(centerItemIndex) {
        if (centerItemIndex in items.indices) {
            onSelected(centerItemIndex, items[centerItemIndex])
        }
    }

    // Calculate wheel height based on visible items
    val totalVisibleItems = visibleCount.coerceAtLeast(1)
    val wheelHeightDp = (totalVisibleItems * itemHeight).dp


    Box(
        modifier = modifier
            .height(wheelHeightDp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
            flingBehavior = rememberSnapFlingBehavior(lazyListState)
        ) {
            itemsIndexed(items) { index, item ->
                // Distance from center
                val distanceFromCenter = (index - centerItemIndex).absoluteValue.toFloat()

                // Interpolate visual properties based on distance from center
                val scaleFactor = lerp(
                    start = 0.75f,  // Minimum scale for edge items
                    stop = 1.0f,      // Full scale for center item
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )
                val alphaFactor = lerp(
                    start = 0.3f,     // More transparent for edge items
                    stop = 1.0f,      // Fully opaque for center item
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )
                // Selection styling logic
                val isSelected = (index == centerItemIndex)
                val textColor = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                }
                val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

                Box(
                    modifier = Modifier
                        .height(itemHeight.dp)
                        .fillMaxWidth()
                        .scale(scaleFactor)
                        .alpha(alphaFactor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.toString(),
                        color = textColor,
                        fontWeight = fontWeight,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
