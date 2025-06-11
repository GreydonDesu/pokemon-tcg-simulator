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
import androidx.compose.runtime.collectAsState
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
import de.thro.packsimulator.viewmodel.AccountViewModel
import di.appModule
import kotlinx.browser.document
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialize Koin
    startKoin {
        modules(appModule) // Load the Koin module
    }

    // Start the Compose application
    ComposeViewport(document.body!!) {
        ResponsiveLayout {
            App() // Your main Composable function
        }
    }
}

@Composable
fun App() {
    // State to track the current page
    var currentPage by remember { mutableStateOf("SetPage") }

    // State for error messages
    var errorMessage by remember { mutableStateOf("") }

    // ScaffoldState for managing the snackbar
    val scaffoldState = rememberScaffoldState()

    // AccountViewModel instance
    val accountViewModel = remember { AccountViewModel() }

    // Observe login state from AccountViewModel
    val isLoggedIn by accountViewModel.isLoggedIn.collectAsState()

    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopBarContentItem(
                    onAddCardsClick = { currentPage = "SetPage" },
                    onInventoryClick = { currentPage = if (isLoggedIn) "AccountPage" else "LoginPage" },
                    accountViewModel = accountViewModel,
                )
            },
        ) {
            // Render the appropriate page based on the current state
            when (currentPage) {
                "SetPage" -> SetView(scaffoldState = scaffoldState)

                "LoginPage" ->
                    LoginView(
                        onLoginSuccess = {
                            currentPage = "AccountPage" // Navigate to AccountPage after login
                        },
                        showError = { message -> errorMessage = message },
                    )

                "AccountPage" ->
                    AccountView(
                        onLogoutClick = {
                            accountViewModel.logout() // Clear the logged-in account
                            currentPage = "LoginPage" // Navigate back to LoginPage after logout
                        })

                else -> SetView(scaffoldState = scaffoldState)
            }

            // Show the snackbar if there’s an error message
            if (errorMessage.isNotEmpty()) {
                // Trigger the snackbar
                LaunchedEffect(errorMessage) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = errorMessage,
                        actionLabel = "Dismiss",
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
            modifier =
                Modifier.width(currentWidth)
                    .fillMaxHeight() // Fill the height of the screen
                    .padding(horizontal = 16.dp) // Optional padding for spacing
                    .align(Alignment.TopCenter)) {
            content() // Render the provided content
        }
    }
}
