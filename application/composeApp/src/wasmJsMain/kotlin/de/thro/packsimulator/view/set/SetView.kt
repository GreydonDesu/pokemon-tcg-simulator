package de.thro.packsimulator.view.set

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.thro.packsimulator.viewmodel.set.SetBriefManager
import de.thro.packsimulator.data.set.SetBrief

// Scroll amount
const val SCROLL_VALUE = 1200

@Composable
fun SetSelectPage() {

    val setBriefs by remember { derivedStateOf { SetBriefManager.setBriefs } }
    var selectedSetBrief by remember { mutableStateOf<SetBrief?>(null) } // Track the selected SetBrief

    LaunchedEffect(Unit) {
        if (setBriefs.isEmpty()) {
            println("Fetched Sets are 0. Searching for Sets...")
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
        // Layout with the top card list and the bottom dynamic card
        Column(modifier = Modifier.fillMaxSize()) {
            // Top card list
            SetBriefList(
                setBriefList = setBriefs,
                onItemClick = { selectedSetBrief = it } // Set the selected SetBrief on click
            )

            // Bottom dynamic card (takes the rest of the page)
            if (selectedSetBrief != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Take up the remaining vertical space
                ) {
                    SetDetails(selectedSetBrief!!.name)
                }
            }
        }
    }
}