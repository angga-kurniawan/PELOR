package com.example.pelor.PartHome

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun CustomBottomSheetScaffold(
    sheetContent: @Composable (isExpanded: Boolean) -> Unit,
    mainContent: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit = {},
    onExpandRequest: ((() -> Unit, () -> Unit) -> Unit)? = null
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val peekHeight = 62.dp
    val halfHeight = screenHeightDp / 2
    val sheetHeight = screenHeightDp

    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val screenHeightPx = with(receiver = density, block = { screenHeightDp.toPx() })
    val peekHeightPx = with(receiver = density, block = { peekHeight.toPx() })
    val halfHeightPx = with(receiver = density, block = { halfHeight.toPx() })
    val sheetHeightPx = with(receiver = density, block = { sheetHeight.toPx() })

    val fullOffset = screenHeightPx - sheetHeightPx
    val halfOffset = screenHeightPx - halfHeightPx
    val miniOffset = screenHeightPx - peekHeightPx

    val offsetY = remember { Animatable(miniOffset) }


    val expandToFull: () -> Unit = {
        coroutineScope.launch {
            offsetY.animateTo(halfOffset)
        }
    }

    val collapseSheet: () -> Unit = {
        coroutineScope.launch {
            offsetY.animateTo(miniOffset)
        }
    }

    onExpandRequest?.invoke(expandToFull, collapseSheet)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        content = {
            mainContent()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp, end = 16.dp),
                contentAlignment = Alignment.BottomEnd,
                content = {
                    floatingActionButton()
                }
            )

            Box(
                modifier = Modifier
                    .offset { IntOffset(0, offsetY.value.toInt()) }
                    .fillMaxWidth()
                    .height(sheetHeight)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
                    )
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta ->
                            coroutineScope.launch {
                                val newOffset =
                                    (offsetY.value + delta).coerceIn(fullOffset, miniOffset)
                                offsetY.snapTo(newOffset)
                            }
                        },
                        onDragStopped = {
                            coroutineScope.launch {
                                val positions = listOf(fullOffset, halfOffset, miniOffset)
                                val nearest =
                                    positions.minByOrNull { abs(it - offsetY.value) } ?: miniOffset
                                offsetY.animateTo(nearest)
                            }
                        }
                    ),
                content = {
                    val isExpanded = offsetY.value <= halfOffset
                    sheetContent(isExpanded)
                }
            )
        }
    )
}