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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import de.thro.packsimulator.cardset.CardSetManager
import de.thro.packsimulator.cardset.data.SetBase
import de.thro.packsimulator.cardset.ui.CardSetItem
import kotlinx.coroutines.launch

@Composable
fun CardSetOverview() {
    // Trigger data fetching when the app launches
    LaunchedEffect(Unit) {
        CardSetManager.fetchCardSets()
    }

    // Observe the cardSets state
    val cardSets by remember { derivedStateOf { CardSetManager.cardSets } }

    // UI
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Pokemon Card Sets") })
            }
        ) {
            if (cardSets.isEmpty()) {
                // Show loading indicator while data is being fetched
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                // Show card sets once data is loaded
                CardSetList(cardSets)
            }
        }
    }
}

@Composable
fun CardSetList(cardSets: List<SetBase>) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val scrollAmount = 1000

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
                ) {
                    cardSets.forEach { cardSet ->
                        CardSetItem(cardSet)
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