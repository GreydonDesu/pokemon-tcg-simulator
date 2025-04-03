package de.thro.packsimulator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.thro.packsimulator.account.data.UserAccountManager

@Composable
fun LoginPage(
    onLoginSuccess: (String) -> Unit, // Callback for navigating to the inventory page
    showError: (String) -> Unit // Callback to show error messages
) {
    var loginUsername by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }
    var registerUsername by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Login Section
            Text("Login", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = loginUsername,
                onValueChange = { loginUsername = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.33f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = loginPassword,
                onValueChange = { loginPassword = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(0.33f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (UserAccountManager.loginUser(loginUsername, loginPassword)) {
                        onLoginSuccess(loginUsername) // Pass the username to the callback
                    } else {
                        showError("Invalid username or password!")
                    }
                },
                modifier = Modifier.fillMaxWidth(0.33f)
            ) {
                Text("Log In")
            }

            // Divider with "OR"
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f))
                Text("OR", modifier = Modifier.padding(horizontal = 8.dp))
                Divider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Register Section
            Text("Register", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = registerUsername,
                onValueChange = { registerUsername = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(0.33f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = registerPassword,
                onValueChange = { registerPassword = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(0.33f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val result = UserAccountManager.registerUser(registerUsername, registerPassword)
                    if (result == "Registration successful") {
                        showError(result) // Show success message
                    } else {
                        showError(result) // Show the error message
                    }
                },
                modifier = Modifier.fillMaxWidth(0.33f)
            ) {
                Text("Register")
            }
        }
    }
}

