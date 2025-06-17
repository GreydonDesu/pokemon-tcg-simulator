package de.thro.packsimulator.view.miscellaneous.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import de.thro.packsimulator.model.CardModel

@Composable
fun CardBriefItem(card: CardModel) {
  Card(
    modifier = Modifier.fillMaxWidth().padding(8.dp),
    elevation = 4.dp,
    shape = RoundedCornerShape(8.dp),
  ) {
    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
      // Display name and Id at the top
      Text(
        text = card.name,
        modifier = Modifier.align(Alignment.Start),
        style = MaterialTheme.typography.h6,
      )
      Text(
        text = "[${card.id}]",
        modifier = Modifier.align(Alignment.Start),
        style = MaterialTheme.typography.body2,
      )
      Spacer(modifier = Modifier.height(8.dp))

      // Display card image below the text
      AsyncImage(
        model = ImageRequest.Builder(PlatformContext.INSTANCE).data(card.image).build(),
        contentDescription = "Image for ${card.name}",
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
      )
    }
  }
}
