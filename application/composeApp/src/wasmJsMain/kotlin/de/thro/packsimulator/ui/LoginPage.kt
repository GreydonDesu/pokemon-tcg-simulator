package de.thro.packsimulator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginPage(
    onLoginSuccess: () -> Unit, // Callback for successful login
    onBackClick: () -> Unit // Callback for back navigation
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("This is the LoginPage")
            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(onClick = onLoginSuccess) {
                Text("Log In")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Back Button
            Button(onClick = onBackClick) {
                Text("Back")
            }
        }
    }
}