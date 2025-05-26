package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import de.thro.packsimulator.model.CardModel
import de.thro.packsimulator.service.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class AccountViewModel : ViewModel() {
    private val _token = MutableStateFlow<String?>(null) // The current token

    private val _inventory = MutableStateFlow<List<CardModel>>(emptyList()) // User's inventory
    val inventory: StateFlow<List<CardModel>> get() = _inventory

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> get() = _statusMessage

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Unhandled exception: ${throwable.message}") // Log the unexpected exception
    }

    // Login function
    fun login(username: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                val token = ApiService.login(username, password) // Call the login API
                _token.value = token
                _isLoggedIn.value = true
                _statusMessage.value = "Login successful"
                fetchInventory() // Fetch inventory after login
            } catch (e: IOException) {
                // Handle network-related exceptions
                _statusMessage.value = "Network error: ${e.message}"
                println("IOException during login: ${e.message}") // Log the exception
                _isLoggedIn.value = false
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 401 Unauthorized, 404 Not Found)
                _statusMessage.value = "HTTP error: ${e.message}"
                println("HttpException during login: ${e.message}") // Log the exception
                _isLoggedIn.value = false
            } catch (e: SerializationException) {
                // Handle JSON parsing errors
                _statusMessage.value = "Invalid server response"
                println("SerializationException during login: ${e.message}") // Log the exception
                _isLoggedIn.value = false
            }
        }
    }

    // Register function
    fun register(username: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            try {
                // Call the register API
                ApiService.registerAccount(username, password)
                _statusMessage.value = "Registration successful. Please log in."
            } catch (e: IOException) {
                // Handle network-related exceptions
                _statusMessage.value = "Network error: ${e.message}"
                println("IOException during register: ${e.message}") // Log the exception
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 400 Bad Request, 409 Conflict)
                _statusMessage.value = "HTTP error: ${e.message}"
                println("HttpException during register: ${e.message}") // Log the exception
            } catch (e: SerializationException) {
                // Handle JSON parsing errors
                _statusMessage.value = "Invalid server response"
                println("SerializationException during register: ${e.message}") // Log the exception
            }
        }
    }

    // Fetch the inventory of the logged-in user
    fun fetchInventory() {
        viewModelScope.launch(exceptionHandler) {
            try {
                val inventory = ApiService.getInventory() // Fetch inventory from the API
                _inventory.value = inventory
            } catch (e: IOException) {
                // Handle network-related exceptions
                _statusMessage.value = "Network error: ${e.message}"
                println("IOException during fetchInventory: ${e.message}") // Log the exception
            } catch (e: HttpException) {
                // Handle HTTP errors (e.g., 401 Unauthorized, 404 Not Found)
                _statusMessage.value = "HTTP error: ${e.message}"
                println("HttpException during fetchInventory: ${e.message}") // Log the exception
            } catch (e: SerializationException) {
                // Handle JSON parsing errors
                _statusMessage.value = "Invalid server response"
                println("SerializationException during fetchInventory: ${e.message}") // Log the exception
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
