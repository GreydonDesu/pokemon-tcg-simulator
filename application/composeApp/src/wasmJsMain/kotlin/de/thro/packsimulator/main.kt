package de.thro.packsimulator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import de.thro.packsimulator.ui.SetSelectPage
import de.thro.packsimulator.ui.LoginPage
import de.thro.packsimulator.ui.InventoryPage
import de.thro.packsimulator.ui.item.TopBarContentItem
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        ResponsiveLayout {
            App()
        }
    }
}

@Composable
fun App() {
    // State to track the current page and login status
    var currentPage by remember { mutableStateOf("SetSelectPage") }
    var isLoggedIn by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopBarContentItem(
                    onAddCardsClick = { currentPage = "SetSelectPage" },
                    onInventoryClick = {
                        currentPage = if (isLoggedIn) "InventoryPage" else "LoginPage"
                    }
                )
            }
        ) {
            // Render the appropriate page based on the current state
            when (currentPage) {
                "SetSelectPage" -> SetSelectPage()
                "LoginPage" -> {
                    LoginPage(
                        onLoginSuccess = {
                            isLoggedIn = true // Mark the user as logged in
                            currentPage = "InventoryPage" // Navigate to InventoryPage after login
                        },
                        onBackClick = {
                            currentPage = "SetSelectPage" // Go back to SetSelectPage
                        }
                    )
                }

                "InventoryPage" -> InventoryPage(
                    onBackClick = {
                        currentPage = "SetSelectPage" // Go back to SetSelectPage
                    }
                )
            }
        }
    }
}

@Composable
fun ResponsiveLayout(content: @Composable () -> Unit) {
    // Define a maximum width for your app layout
    val maxWidth = 1200.dp

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize() // Fill the available screen space
    ) {
        // Check the current screen width (via maxWidth constraints)
        val currentWidth = maxWidth.coerceAtMost(this.maxWidth)

        // Center the content and apply the responsive width
        Box(
            modifier = Modifier
                .width(currentWidth)
                .fillMaxHeight() // Fill the height of the screen
                .padding(horizontal = 16.dp) // Optional padding for spacing
                .align(Alignment.TopCenter)
        ) {
            content() // Render the provided content
        }
    }
}