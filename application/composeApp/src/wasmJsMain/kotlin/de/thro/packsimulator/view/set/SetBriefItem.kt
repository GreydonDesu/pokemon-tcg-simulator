package de.thro.packsimulator.view.set

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
import de.thro.packsimulator.model.SetModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetBriefItem(sets: SetModel, onClick: () -> Unit) {
  Card(
    modifier = Modifier.padding(8.dp),
    onClick = onClick, // Trigger the passed onClick callback
  ) {
    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
      Column {
        Row {
          if (sets.symbol!!.isNotEmpty()) {
            AsyncImage(
              model = ImageRequest.Builder(PlatformContext.INSTANCE).data(sets.symbol).build(),
              contentDescription = "Symbol for ${sets.name}",
              modifier = Modifier.size(24.dp),
            )
          }
          Spacer(Modifier.size(2.dp))
          Text(text = sets.name, style = MaterialTheme.typography.h6)
          Spacer(Modifier.size(2.dp))
          Text(text = "[${sets.id}]", style = MaterialTheme.typography.body2)
        }
        Text(text = "Total Cards: ${sets.totalCards}", style = MaterialTheme.typography.body2)
      }
      Spacer(Modifier.size(16.dp))
      if (sets.logo!!.isNotEmpty()) {
        AsyncImage(
          model = ImageRequest.Builder(PlatformContext.INSTANCE).data(sets.logo).build(),
          contentDescription = "Logo for ${sets.name}",
          modifier = Modifier.size(128.dp),
        )
      }
    }
  }
}
