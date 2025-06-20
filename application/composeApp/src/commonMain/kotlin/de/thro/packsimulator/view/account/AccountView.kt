package de.thro.packsimulator.view.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thro.packsimulator.view.set.details.SetDetailsCardList
import de.thro.packsimulator.viewmodel.AccountViewModel
import org.koin.compose.koinInject

@Composable
fun AccountView(
    onLogoutClick: () -> Unit // Callback for logout action
) {
    // Inject AccountViewModel using Koin
    val accountViewModel: AccountViewModel = koinInject()

    // Observe state from AccountViewModel
    val inventory by accountViewModel.inventory.collectAsState()
    val isLoggedIn by accountViewModel.isLoggedIn.collectAsState()
    val statusMessage by accountViewModel.statusMessage.collectAsState()

    // Trigger navigation on logout
    if (!isLoggedIn) {
        onLogoutClick()
    }

    // Trigger inventory refresh when the view is entered
    LaunchedEffect(Unit) {
        if (isLoggedIn) accountViewModel.fetchInventory()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter, // Align everything at the top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top, // Arrange content from the top
            modifier = Modifier.padding(16.dp),
        ) {
            if (!isLoggedIn) {
                Text(
                    text = statusMessage,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                // Display the username
                Text(
                    "Logged in as ${accountViewModel.username.value}",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                // Logout button with an icon
                Button(
                    onClick = {
                        accountViewModel.logout() // Clear the logged-in account
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close, // Logout icon
                        contentDescription = "Logout",
                        modifier = Modifier.padding(end = 8.dp), // Add spacing between icon and text
                    )
                    Text("Logout")
                }

                // Inventory Section
                Text("Your Inventory", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                if (inventory.isNotEmpty()) {
                    // Use SetDetailsCardList to display the inventory cards
                    SetDetailsCardList(cards = inventory, startExpanded = true)
                } else {
                    Text("Your inventory is empty.")
                }
            }
        }
    }
}
