package de.thro.packsimulator.view.set.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import de.thro.packsimulator.data.set.Set
import de.thro.packsimulator.manager.APIManager
import de.thro.packsimulator.manager.AccountManager
import de.thro.packsimulator.viewmodel.set.SetDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun SetDetails(
    setId: String,
    scaffoldState: ScaffoldState // Accept ScaffoldState from the parent
) {
    var setData by remember { mutableStateOf<Set?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val currentAccount = AccountManager.getCurrentAccount() // Fetch the currently logged-in account

    // Fetch data when the composable is displayed
    LaunchedEffect(setId) {
        isLoading = true
        errorMessage = null
        try {
            setData = APIManager.getSet(setId)
        } catch (e: Exception) {
            errorMessage = "Error fetching set details: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colors.error)
        }
    } else if (setData != null) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(Modifier.fillMaxSize()) {
                // Logo at the top-right corner
                if (setData!!.getLogoUrl("png") != "null.png") {
                    AsyncImage(
                        model = ImageRequest.Builder(PlatformContext.INSTANCE)
                            .data(setData!!.getLogoUrl("png"))
                            .build(),
                        contentDescription = "Logo for ${setData!!.name}",
                        modifier = Modifier
                            .size(256.dp)
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                    )
                }

                // Main content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                ) {
                    // Title and release date
                    Text(
                        text = "${setData!!.name} [${setData!!.id}]",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Release Date: ${setData!!.releaseDate}",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Card Count Details Table
                    Text(
                        text = "Card Count Details:",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Render a 2x5 grid for card count
                    Column {
                        Row {
                            Text(
                                text = "Total Cards:",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${setData!!.cardCount.total}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row {
                            Text(
                                text = "Official Cards:",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${setData!!.cardCount.official}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row {
                            Text(
                                text = "Reverse Holo:",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${setData!!.cardCount.reverse}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row {
                            Text(
                                text = "Holo Cards:",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${setData!!.cardCount.holo}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                        }
                        Row {
                            Text(
                                text = "First Edition:",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${setData!!.cardCount.firstEd}",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }

                    Button(
                        onClick = {
                            // Delegate the logic to SetDetailsViewModel
                            SetDetailsViewModel.openPack(
                                account = currentAccount,
                                cards = setData!!.cards,
                                scaffoldState = scaffoldState,
                                scope = scope
                            )
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(text = "Open Pack")
                    }

                    // Render CardBrief List
                    if (setData!!.cards.isNotEmpty()) {
                        SetDetailsCardList(cards = setData!!.cards)
                    } else {
                        Text(text = "No cards available.", style = MaterialTheme.typography.body2)
                    }
                }
            }
        }
    }
}
