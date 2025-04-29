package de.thro.packsimulator.view.set

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import de.thro.packsimulator.manager.APIManager
import de.thro.packsimulator.data.set.Set

@Composable
fun SetDetails(setId: String) {
    // State to hold the fetched Set data
    var setData by remember { mutableStateOf<Set?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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

    // UI
    if (isLoading) {
        // Show a loading indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        // Show an error message
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colors.error)
        }
    } else if (setData != null) {
        // Display the Set details
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Set ID: ${setData!!.id}",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Set Name: ${setData!!.name}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total Cards: ${setData!!.cardCount.total}",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Release Date: ${setData!!.releaseDate}",
                    style = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Set Logo
                if (setData!!.getLogoUrl("png") != "null.png") {
                    AsyncImage(
                        model = ImageRequest.Builder(PlatformContext.INSTANCE)
                            .data(setData!!.getLogoUrl("png")).build(),
                        contentDescription = "Logo for ${setData!!.name}",
                        modifier = Modifier.size(128.dp)
                    )
                } else {
                    Text(text = "No Logo Available")
                }
            }
        }
    }
}
