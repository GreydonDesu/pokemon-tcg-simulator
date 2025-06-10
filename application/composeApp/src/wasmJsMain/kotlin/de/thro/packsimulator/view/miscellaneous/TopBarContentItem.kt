package de.thro.packsimulator.view.miscellaneous

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.thro.packsimulator.viewmodel.AccountViewModel

@Composable
fun TopBarContentItem(onAddCardsClick: () -> Unit, onInventoryClick: () -> Unit, accountViewModel: AccountViewModel) {
  val isLoggedIn by accountViewModel.isLoggedIn.collectAsState()

  TopAppBar(
    title = { Text("Pokémon TCG Pack Simulator") },
    elevation = 4.dp,
    actions = {
      // Add Cards Button
      IconWithTextButton(
        icon = {
          Icon(Icons.Filled.AddCircle, contentDescription = "Add Cards", tint = Color.White)
        },
        text = "Add Cards",
        onClick = onAddCardsClick,
      )

      Spacer(modifier = Modifier.width(8.dp)) // Space between buttons

      // Login / Account Button
      if (isLoggedIn) {
        IconWithTextButton(
          icon = {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Account", tint = Color.White)
          },
          text = "Account",
          onClick = onInventoryClick,
        )
      } else {
        IconWithTextButton(
          icon = {
            Icon(Icons.Filled.Create, contentDescription = "Login", tint = Color.White)
          },
          text = "Login",
          onClick = onInventoryClick,
        )
      }
    },
  )
}

@Composable
fun IconWithTextButton(icon: @Composable () -> Unit, text: String, onClick: () -> Unit) {
  TextButton(onClick = onClick, modifier = Modifier.padding(horizontal = 8.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      // Icon
      icon()
      Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text
      // Text
      Text(text = text, fontSize = 14.sp, color = Color.White)
    }
  }
}
