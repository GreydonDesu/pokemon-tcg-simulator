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
import de.thro.packsimulator.data.set.SetBrief
import de.thro.packsimulator.viewmodel.set.SetBriefViewModel

// Scroll amount
const val SCROLL_VALUE = 1200

@Composable
fun SetView() {
    val setBriefs by remember { derivedStateOf { SetBriefViewModel.setBriefs } }
    var selectedSetBrief by remember { mutableStateOf<SetBrief?>(null) } // Track the selected SetBrief

    LaunchedEffect(Unit) {
        SetBriefViewModel.fetchSetBriefs()
    }

    val fetchedSetBriefs = setBriefs.value

    if (fetchedSetBriefs.isEmpty()) {
        // Show loading indicator while data is being fetched
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Layout with top list and bottom details
        Column(modifier = Modifier.fillMaxSize()) {
            // Top list of set briefs
            SetBriefList(
                setBriefList = fetchedSetBriefs,
                onItemClick = { selectedSetBrief = it }
            )

            // Bottom details section
            if (selectedSetBrief != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Take remaining vertical space
                ) {
                    SetDetails(selectedSetBrief!!.id)
                }
            }
        }
    }
}