package com.amirnlz.tasko.core.ui.components

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
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
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Animate or jump to initialIndex on first composition
    LaunchedEffect(items, initialIndex) {
        if (animateInitialScroll) {
            lazyListState.animateScrollToItem(initialIndex)
        } else {
            lazyListState.scrollToItem(initialIndex)
        }
    }

    // We must remember the derivedStateOf to avoid "creating state object without remember"
    val centerItemIndex by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val viewportCenter =
                layoutInfo.viewportStartOffset + (layoutInfo.viewportSize.height / 2)
            val closestItem = layoutInfo.visibleItemsInfo.minByOrNull { info ->
                val itemCenter = info.offset + (info.size / 2)
                kotlin.math.abs(itemCenter - viewportCenter)
            }
            closestItem?.index ?: 0
        }
    }

    // Notify the caller whenever the center item changes
    LaunchedEffect(centerItemIndex) {
        if (centerItemIndex in items.indices) {
            onSelected(centerItemIndex, items[centerItemIndex])
        }
    }

    val totalVisibleItems = visibleCount.coerceAtLeast(1)
    val wheelHeightDp = (totalVisibleItems * itemHeight).dp

    Box(
        modifier = modifier
            .height(wheelHeightDp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // The vertical lazy list
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

                // Scale + alpha for "wheel" effect
                val scaleFactor = lerp(
                    start = 0.75f,
                    stop = 1.0f,
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )
                val alphaFactor = lerp(
                    start = 0.3f,
                    stop = 1.0f,
                    fraction = (1f - distanceFromCenter / totalVisibleItems).coerceIn(0f, 1f)
                )

                // For the center item, apply bold + primary color. Otherwise normal style.
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
                        .alpha(alphaFactor)
                        .clickable {
                            scope.launch {
                                lazyListState.animateScrollToItem(index)
                            }
                        },
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
