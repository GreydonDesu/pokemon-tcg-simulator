package de.thro.packsimulator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import de.thro.packsimulator.view.account.AccountView
import de.thro.packsimulator.view.login.LoginView
import de.thro.packsimulator.view.miscellaneous.TopBarContentItem
import de.thro.packsimulator.view.set.SetView
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

    // Track the logged-in username
    var loggedInUsername by remember { mutableStateOf("") }

    // State for error messages
    var errorMessage by remember { mutableStateOf("") }

    // ScaffoldState for managing the snackbar
    val scaffoldState = rememberScaffoldState()

    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState, // Attach the ScaffoldState
            topBar = {
                TopBarContentItem(
                    onAddCardsClick = { currentPage = "SetPage" },
                    onInventoryClick = {
                        currentPage = if (isLoggedIn) "AccountPage" else "LoginPage"
                    }
                )
            }
        ) {
            // Render the appropriate page based on the current state
            when (currentPage) {
                "SetPage" -> SetView()
                "LoginPage" -> {
                    LoginView(
                        onLoginSuccess = { username ->
                            isLoggedIn = true
                            loggedInUsername = username // Store the logged-in username
                            currentPage = "AccountPage" // Navigate to AccountPage after login
                        },
                        showError = { message ->
                            errorMessage = message
                        }
                    )
                }

                "AccountPage" -> AccountView(
                    username = loggedInUsername, // Pass the logged-in username
                    onLogoutClick = {
                        isLoggedIn = false
                        loggedInUsername = "" // Clear the username
                        currentPage = "LoginPage" // Navigate back to LoginPage after logout
                    }
                )
                else -> SetView()
            }

            // Show the snackbar if there’s an error message
            if (errorMessage.isNotEmpty()) {
                // Trigger the snackbar
                LaunchedEffect(errorMessage) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = errorMessage,
                        actionLabel = "Dismiss"
                    )
                    errorMessage = "" // Reset the error message after the snackbar is shown
                }
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