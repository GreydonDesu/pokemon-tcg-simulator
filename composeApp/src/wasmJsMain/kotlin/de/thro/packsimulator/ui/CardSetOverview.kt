package de.thro.packsimulator.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.thro.packsimulator.cardset.data.CardSet
import de.thro.packsimulator.cardset.ui.CardSetItem
import de.thro.packsimulator.cardset.CardSetManager

@Composable
fun CardSetOverview() {
    // Create an instance of CardSetManager
    val cardSetManager = remember { CardSetManager() }

    // Trigger data fetching when the app launches
    LaunchedEffect(Unit) {
        cardSetManager.fetchCardSets()
    }

    // Observe the cardSets state
    val cardSets by remember { derivedStateOf { cardSetManager.cardSets } }

    // UI
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Pokemon Card Sets") })
            }
        ) {
            if (cardSets.isEmpty()) {
                // Show loading indicator while data is being fetched
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
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
fun CardSetList(cardSets: List<CardSet>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cardSets) { cardSet ->
            CardSetItem(cardSet)
        }
    }
}
