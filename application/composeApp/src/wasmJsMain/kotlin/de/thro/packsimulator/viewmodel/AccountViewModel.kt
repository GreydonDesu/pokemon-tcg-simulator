package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.model.CardModel
import de.thro.packsimulator.service.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    private val _token = MutableStateFlow<String?>(null) // The current token
    val token: StateFlow<String?> get() = _token

    private val _inventory = MutableStateFlow<List<CardModel>>(emptyList()) // User's inventory
    val inventory: StateFlow<List<CardModel>> get() = _inventory

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> get() = _statusMessage

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    // Login function
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val token = ApiService.login(username, password) // Call the login API
                _token.value = token
                _isLoggedIn.value = true
                _statusMessage.value = "Login successful"
                fetchInventory() // Fetch inventory after login
            } catch (e: Exception) {
                _statusMessage.value = e.message ?: "Error during login"
                _isLoggedIn.value = false
            }
        }
    }

    // Fetch the inventory of the logged-in user
    fun fetchInventory() {
        viewModelScope.launch {
            try {
                val inventory = ApiService.getInventory() // Fetch inventory from the API
                _inventory.value = inventory
            } catch (e: Exception) {
                _statusMessage.value = e.message ?: "Error fetching inventory"
            }
        }
    }

    // Logout function
    fun logout() {
        _token.value = null
        _inventory.value = emptyList()
        _isLoggedIn.value = false
        ApiService.logout() // Clear the token in ApiService
        _statusMessage.value = "Logged out successfully"
    }
}
