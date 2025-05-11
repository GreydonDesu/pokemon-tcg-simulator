package de.thro.packsimulator.view.set.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thro.packsimulator.data.card.CardBrief
import de.thro.packsimulator.view.miscellaneous.card.CardBriefItem

const val ROW_NUMBERS = 5

@Composable
fun SetDetailsCardList(cards: List<CardBrief>, startExpanded: Boolean = false) {
    var isExpanded by remember { mutableStateOf(startExpanded) } // Tracks whether the grid is expanded

    Column(modifier = Modifier.fillMaxWidth()) {
        // Expand/Collapse button
        Button(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = if (isExpanded) "Hide Cards" else "Show Cards")
        }

        // Animated visibility for the grid
        AnimatedVisibility(visible = isExpanded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(ROW_NUMBERS), // Display 2 items per row
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cards) { card ->
                    CardBriefItem(card = card)
                }
            }
        }
    }
}
