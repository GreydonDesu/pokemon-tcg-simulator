package de.thro.packsimulator.view.set

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    // Collect the StateFlow from the ViewModel
    val setBriefs by SetBriefViewModel.setBriefs.collectAsState()

    // Mutable state for tracking the selected SetBrief
    var selectedSetBrief by remember { mutableStateOf<SetBrief?>(null) }

    // Fetch data when the composable is first composed
    LaunchedEffect(Unit) {
        SetBriefViewModel.fetchSetBriefs()
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
        // Layout with top list and bottom details
        Column(modifier = Modifier.fillMaxSize()) {
            // Top list of set briefs
            SetBriefList(
                setBriefList = setBriefs,
                onItemClick = { selectedSetBrief = it }
            )

            // Bottom details section
            selectedSetBrief?.let { brief ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Take remaining vertical space
                ) {
                    SetDetails(brief.id)
                }
            }
        }
    }
}