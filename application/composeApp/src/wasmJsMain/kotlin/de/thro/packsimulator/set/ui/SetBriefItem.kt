package de.thro.packsimulator.set.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import de.thro.packsimulator.set.data.SetBrief

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetBriefItem(setBrief: SetBrief) {
    Card(
        modifier = Modifier.padding(8.dp),
        onClick = { println("onClick: Set ID ${setBrief.id}") }) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Row {
                    if (setBrief.getSymbolUrl("png") != "null.png") {
                        AsyncImage(
                            model = ImageRequest.Builder(PlatformContext.INSTANCE)
                                .data(setBrief.getSymbolUrl("png")).build(),
                            contentDescription = "Symbol for ${setBrief.name}",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.size(2.dp))
                    Text(text = setBrief.name, style = MaterialTheme.typography.h6)
                    Spacer(Modifier.size(2.dp))
                    Text(text = "[${setBrief.id}]", style = MaterialTheme.typography.body2)
                }
                Text(
                    text = "Total Cards: ${setBrief.cardCount.total}",
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(Modifier.size(16.dp))
            if (setBrief.getLogoUrl("png") != "null.png") {
                AsyncImage(
                    model = ImageRequest.Builder(PlatformContext.INSTANCE)
                        .data(setBrief.getLogoUrl("png")).build(),
                    contentDescription = "Logo for ${setBrief.name}",
                    modifier = Modifier.size(128.dp)
                )
            }
        }
    }
}