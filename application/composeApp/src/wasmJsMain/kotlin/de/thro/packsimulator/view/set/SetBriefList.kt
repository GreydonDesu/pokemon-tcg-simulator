package de.thro.packsimulator.view.set

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.thro.packsimulator.model.SetModel
import kotlinx.coroutines.launch

@Composable
fun SetBriefList(
    setBriefList: List<SetModel>,
    onItemClick: (SetModel) -> Unit // Pass the click callback
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    // Scroll left by the defined amount, ensuring we don't scroll out of bounds
                    coroutineScope.launch {
                        val targetPosition = (scrollState.value - SCROLL_VALUE).coerceAtLeast(0)
                        scrollState.animateScrollTo(targetPosition)
                    }

                },
                // Disable button if already at the start
                enabled = scrollState.value > 0
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Scroll Left")
            }

            Box(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .wrapContentWidth()
                        .align(Alignment.Center)
                ) {
                    setBriefList.forEach { setBrief ->
                        SetBriefItem(sets = setBrief, onClick = { onItemClick(setBrief) })
                    }
                }
            }

            IconButton(
                onClick = {
                    // Scroll right by the defined amount, ensuring we don't scroll out of bounds
                    coroutineScope.launch {
                        val targetPosition = (scrollState.value + SCROLL_VALUE)
                            .coerceAtMost(scrollState.maxValue)
                        scrollState.animateScrollTo(targetPosition)
                    }
                },
                // Disable button if already at the end
                enabled = scrollState.value < scrollState.maxValue
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Scroll Right")
            }
        }
        HorizontalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}