package de.thro.packsimulator.view.set

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.thro.packsimulator.model.SetModel
import de.thro.packsimulator.view.set.details.SetDetails
import de.thro.packsimulator.viewmodel.SetViewModel
import org.koin.compose.koinInject

// Scroll amount
const val SCROLL_VALUE = 1200

@Composable
fun SetView(scaffoldState: ScaffoldState) {
  // Inject the SetViewModel using Koin
  val setViewModel: SetViewModel = koinInject()

  // Collect the StateFlow from the ViewModel
  val sets by setViewModel.sets.collectAsState() // Observing the list of sets

  // Mutable state for tracking the selected set
  var selectedSet by remember { mutableStateOf<SetModel?>(null) }

  // Fetch data when the composable is first composed
  LaunchedEffect(Unit) { setViewModel.fetchAllSets() }

  if (sets.isEmpty()) {
    // Show loading indicator while data is being fetched
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  } else {
    // Layout with top list and bottom details
    Column(modifier = Modifier.fillMaxSize()) {
      // Top list of set briefs
      SetBriefList(setBriefList = sets, onItemClick = { selectedSet = it })

      // Bottom details section
      selectedSet?.let { brief ->
        Box(
          modifier = Modifier.fillMaxWidth().weight(1f) // Take remaining vertical space
        ) {
          SetDetails(
            setId = brief.id,
            scaffoldState = scaffoldState, // Pass ScaffoldState
            setViewModel = setViewModel, // Pass SetViewModel to SetDetails
          )
        }
      }
    }
  }
}
