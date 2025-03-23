package de.thro.packsimulator.cardset.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thro.packsimulator.cardset.data.CardSet

@Composable
fun CardSetItem(cardSet: CardSet) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = cardSet.name, style = MaterialTheme.typography.h6)
            Text(text = "ID: ${cardSet.id}", style = MaterialTheme.typography.body2)
            Text(
                text = "Total Cards: ${cardSet.cardCount.total}",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total Cards: ${cardSet.getSymbolUrl("png")}",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total Cards: ${cardSet.getLogoUrl("png")}",
                style = MaterialTheme.typography.body2
            )
//            // Load the symbol image
//            Image(
//                painter = rememberAsyncImagePainter(cardSet.getSymbolUrl("png")),
//                contentDescription = "Symbol for ${cardSet.name}",
//                modifier = Modifier.size(64.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Load the logo image
//            Image(
//                painter = rememberAsyncImagePainter(cardSet.getLogoUrl("png")),
//                contentDescription = "Logo for ${cardSet.name}",
//                modifier = Modifier.size(128.dp)
//            )
        }
    }
}