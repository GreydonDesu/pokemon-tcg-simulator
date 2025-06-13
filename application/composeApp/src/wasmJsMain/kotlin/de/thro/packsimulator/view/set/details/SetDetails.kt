package de.thro.packsimulator.view.set.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import de.thro.packsimulator.viewmodel.PackViewModel
import de.thro.packsimulator.viewmodel.SetViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SetDetails(
    setId: String,
    scaffoldState: ScaffoldState, // Accept ScaffoldState from the parent
    setViewModel: SetViewModel, // Inject SetViewModel from parent
) {
  // Inject PackViewModel using Koin
  val packViewModel: PackViewModel = koinInject()

  // Observe state from SetViewModel
  val sets by setViewModel.sets.collectAsState()
  val errorMessage by setViewModel.errorMessage.collectAsState()

  // Find the selected set from the list of sets
  val selectedSet = sets.find { it.id == setId }

  // Observe state from PackViewModel
  val packOpeningState by packViewModel.cards.collectAsState()
  val packErrorMessage by packViewModel.statusMessage.collectAsState()

  val scope = rememberCoroutineScope()

  if (selectedSet == null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, color = MaterialTheme.colors.error)
      } else {
        Text(text = "Set not found.", color = MaterialTheme.colors.error)
      }
    }
  } else {
    Card(modifier = Modifier.fillMaxSize().padding(16.dp)) {
      Box(Modifier.fillMaxSize()) {
        // Logo at the top-right corner
        if (selectedSet.logo!!.isNotEmpty()) {
          AsyncImage(
              model = ImageRequest.Builder(PlatformContext.INSTANCE).data(selectedSet.logo).build(),
              contentDescription = "Logo for ${selectedSet.name}",
              modifier = Modifier.size(256.dp).align(Alignment.TopEnd).padding(16.dp),
          )
        }

        // Main content
        Column(modifier = Modifier.fillMaxSize().padding(16.dp).align(Alignment.CenterStart)) {
          // Title and release date
          Text(
              text = "${selectedSet.name} [${selectedSet.id}]",
              style = MaterialTheme.typography.h5,
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
              text = "Release Date: ${selectedSet.releaseDate}",
              style = MaterialTheme.typography.body2,
          )
          Spacer(modifier = Modifier.height(16.dp))

          // Card Count Details Table
          Text(text = "Card Count Details:", style = MaterialTheme.typography.h6)
          Spacer(modifier = Modifier.height(8.dp))

          Column {
            Row {
              Text(
                  text = "Total Cards:",
                  modifier = Modifier.weight(1f),
                  style = MaterialTheme.typography.body2,
              )
              Text(
                  text = "${selectedSet.totalCards}",
                  modifier = Modifier.weight(1f),
                  style = MaterialTheme.typography.body2,
              )
            }
          }

          // Open Pack Button and Text
          Row(
              verticalAlignment =
                  Alignment.CenterVertically, // Align children vertically to the center
          ) {
            Button(
                onClick = {
                  scope.launch {
                    packViewModel.openPack(setId)
                    val message = packViewModel.statusMessage.value
                    if (message.isNotEmpty()) {
                      scaffoldState.snackbarHostState.showSnackbar(message)
                      packViewModel
                          .clearStatusMessage() // Clear the message after showing the snackbar
                    }
                  }
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
              Text(text = "Open Pack")
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Handle pack opening state
            if (packOpeningState.isNotEmpty()) {
              Column {
                Text(
                    text = "Pack opened! Cards received",
                    style = MaterialTheme.typography.h6,
                )
                Row {
                  packOpeningState.forEach { card ->
                    Text(
                        modifier =
                            Modifier
                                .border(BorderStroke(1.dp, MaterialTheme.colors.primary))
                                .padding(4.dp),
                        text = card.name,
                        style = MaterialTheme.typography.body2)
                  }
                }
              }
            } else if (packErrorMessage.isNotEmpty()) {
              Text(
                  text = packErrorMessage,
                  color = MaterialTheme.colors.error,
                  style = MaterialTheme.typography.body2,
              )
            }
          }

          // Render CardBrief List
          if (selectedSet.cards.isNotEmpty()) {
            SetDetailsCardList(cards = selectedSet.cards)
          } else {
            Text(text = "No cards available.", style = MaterialTheme.typography.body2)
          }
        }
      }
    }
  }
}
