package com.example.pelor.PartHome

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

val LocalScrollingEnabled = compositionLocalOf { true }

@Composable
fun CustomBottomSheetScaffold(
    sheetContent: @Composable (isHalfExpanded: Boolean, isFullyExpanded: Boolean) -> Unit,
    mainContent: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    onExpandRequest: ((expandToFull: () -> Unit, collapse: () -> Unit, expandToHalf: () -> Unit) -> Unit)? = null
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val peekHeight = 62.dp
    val halfHeight = screenHeightDp / 2
    val sheetHeight = screenHeightDp

    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val screenHeightPx = with(density) { screenHeightDp.toPx() }
    val peekHeightPx = with(density) { peekHeight.toPx() }
    val halfHeightPx = with(density) { halfHeight.toPx() }
    val sheetHeightPx = with(density) { sheetHeight.toPx() }

    val fullOffset = screenHeightPx - sheetHeightPx
    val halfOffset = screenHeightPx - halfHeightPx
    val miniOffset = screenHeightPx - peekHeightPx

    val offsetY = remember { Animatable(miniOffset) }

    val expandToFull: () -> Unit = {
        coroutineScope.launch { offsetY.animateTo(fullOffset) }
    }

    val collapseSheet: () -> Unit = {
        coroutineScope.launch { offsetY.animateTo(miniOffset) }
    }
    val expandToHalf: () -> Unit = {
        coroutineScope.launch { offsetY.animateTo(halfOffset) }
    }
    onExpandRequest?.invoke(expandToFull, collapseSheet, expandToHalf)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        mainContent()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp, end = 16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            floatingActionButton()
        }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (offsetY.value > fullOffset) {
                        val newOffset = (offsetY.value + available.y).coerceIn(fullOffset, miniOffset)
                        coroutineScope.launch {
                            offsetY.snapTo(newOffset)
                        }
                        return Offset(0f, available.y)
                    }
                    return Offset.Zero
                }

                override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                    val target = when {
                        offsetY.value < (fullOffset + halfOffset) / 2f -> fullOffset
                        offsetY.value < (halfOffset + miniOffset) / 2f -> halfOffset
                        else -> miniOffset
                    }
                    offsetY.animateTo(target)
                    return Velocity.Zero
                }
            }
        }

        val isHalfExpanded = offsetY.value <= halfOffset + 1f
        val isFullyExpanded = offsetY.value <= fullOffset + 1f

        val cornerRadius = 10.dp
        val dragIndicatorHeight = 4.dp
        val dragIndicatorWidth = 40.dp

        Box(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .fillMaxWidth()
                .height(sheetHeight)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius)
                )
                .nestedScroll(nestedScrollConnection)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        val newOffset = (offsetY.value + delta).coerceIn(fullOffset, miniOffset)
                        coroutineScope.launch {
                            offsetY.snapTo(newOffset)
                        }
                    },
                    onDragStopped = {
                        val positions = listOf(fullOffset, halfOffset, miniOffset)
                        val nearest = positions.minByOrNull { abs(it - offsetY.value) } ?: miniOffset
                        coroutineScope.launch {
                            offsetY.animateTo(nearest)
                        }
                    }
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(dragIndicatorWidth)
                        .height(dragIndicatorHeight)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(50)
                        )
                )

                Spacer(modifier = Modifier.height(10.dp))

                sheetContent(isHalfExpanded, isFullyExpanded)
            }
        }
    }
}