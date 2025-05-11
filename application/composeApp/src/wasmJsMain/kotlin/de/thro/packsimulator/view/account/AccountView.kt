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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.thro.packsimulator.manager.AccountManager
import de.thro.packsimulator.view.set.details.SetDetailsCardList

@Composable
fun AccountView(
    onLogoutClick: () -> Unit // Callback for logout action
) {
    val account = AccountManager.getCurrentAccount()

    if (account == null) {
        // If no account is logged in, show an error or redirect to the login page
        Text("No account is currently logged in.")
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter // Align everything at the top
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top, // Arrange content from the top
            modifier = Modifier.padding(16.dp)
        ) {
            // Display the username
            Text("Welcome, ${account.username}!", modifier = Modifier.padding(bottom = 16.dp))

            // Logout button with an icon
            Button(
                onClick = {
                    AccountManager.setCurrentAccount(null) // Clear the logged-in account
                    onLogoutClick()
                },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close, // Logout icon
                    contentDescription = "Logout",
                    modifier = Modifier.padding(end = 8.dp) // Add spacing between icon and text
                )
                Text("Logout")
            }

            // Inventory Section
            Text("Your Inventory", style = androidx.compose.material.MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))

            if (account.inventory.isNotEmpty()) {
                // Use SetDetailsCardList to display the inventory cards
                SetDetailsCardList(cards = account.inventory, startExpanded = true)
            } else {
                Text("Your inventory is empty.")
            }
        }
    }
}
