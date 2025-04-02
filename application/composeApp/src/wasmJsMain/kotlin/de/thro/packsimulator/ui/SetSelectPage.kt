package de.thro.packsimulator.ui

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.thro.packsimulator.set.SetBriefManager
import de.thro.packsimulator.set.data.SetBrief
import de.thro.packsimulator.set.ui.SetBriefItem
import kotlinx.coroutines.launch

@Composable
fun SetSelectPage() {

    val setBriefs by remember { derivedStateOf { SetBriefManager.setBriefs } }

    LaunchedEffect(Unit) {
        if (setBriefs.isEmpty()) {
            SetBriefManager.fetchSetBriefs()
        }
    }

    if (setBriefs.isEmpty()) {
        // Show loading indicator while data is being fetched
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Show card sets once data is loaded
        SetBriefList(setBriefs)
    }
}

@Composable
fun SetBriefList(setBriefList: List<SetBrief>) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val scrollAmount = 1200

    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    coroutineScope.launch { // Scroll left by the defined amount, ensuring we don't scroll out of bounds
                        val targetPosition = (scrollState.value - scrollAmount).coerceAtLeast(0)
                        scrollState.animateScrollTo(targetPosition)
                    }

                },
                enabled = scrollState.value > 0 // Disable button if already at the start
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
                        SetBriefItem(setBrief)
                    }
                }
            }

            IconButton(
                onClick = {
                    coroutineScope.launch { // Scroll right by the defined amount, ensuring we don't scroll out of bounds
                        val targetPosition = (scrollState.value + scrollAmount)
                            .coerceAtMost(scrollState.maxValue)
                        scrollState.animateScrollTo(targetPosition)
                    }
                },
                enabled = scrollState.value < scrollState.maxValue // Disable button if already at the end
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Scroll Right")
            }
        }
        HorizontalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}