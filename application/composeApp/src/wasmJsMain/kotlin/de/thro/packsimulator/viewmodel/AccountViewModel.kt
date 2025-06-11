package de.thro.packsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.thro.packsimulator.model.CardModel
import de.thro.packsimulator.service.ApiService
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import dev.whyoleg.cryptography.operations.Hasher
import io.ktor.http.HttpStatusCode
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okio.Buffer

private val PASSWORD_REGEX =
    Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
private const val PASSWORD_REQUIREMENTS_MESSAGE =
    "Password must contain at least one uppercase letter, " +
        "one lowercase letter, one number, and one special symbol, " +
        "and be at least 8 characters long."

class AccountViewModel : ViewModel() {

  private val _username = MutableStateFlow("") // Username
  val username: StateFlow<String>
    get() = _username

  private val _inventory = MutableStateFlow<List<CardModel>>(emptyList()) // User's inventory
  val inventory: StateFlow<List<CardModel>>
    get() = _inventory

  private val _statusMessage = MutableStateFlow("")
  val statusMessage: StateFlow<String>
    get() = _statusMessage

  private val _isLoggedIn = MutableStateFlow(false)
  val isLoggedIn: StateFlow<Boolean>
    get() = _isLoggedIn

  private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    println("Unhandled exception: ${throwable.message}") // Log the unexpected exception
  }

  // Register function
  fun register(username: String, password: String) {
    if (username.isBlank()) {
      _statusMessage.value = "Username is required."
      return
    }

    if (!isPasswordValid(password) || password.isBlank()) {
      _statusMessage.value = PASSWORD_REQUIREMENTS_MESSAGE
      return
    }

    val normalizedUsername = username.lowercase() // Normalize username
    viewModelScope.launch(exceptionHandler) {
      val hashedPassword = hashPassword(password) // Hash the password
      val response =
          ApiService.registerAccount(normalizedUsername, hashedPassword) // Make the API call

      when (response.code) {
        HttpStatusCode.OK.value -> { // Success
          val successMessage =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8() // Extract the success message
              } ?: "Registration successful. Please login with the new credentials."
          _statusMessage.value = successMessage
        }
        HttpStatusCode.Conflict.value -> { // Username already taken
          _statusMessage.value = "Username is already taken."
        }
        else -> { // Other errors
          val errorMessage =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
              } ?: "Unknown error"
          _statusMessage.value = "HTTP error (${response.code}): $errorMessage"
        }
      }
    }
  }

  // Login function
  fun login(username: String, password: String) {
    if (username.isBlank() || password.isBlank()) {
      _statusMessage.value = "Username and password are required."
      return
    }

    val normalizedUsername = username.lowercase() // Normalize username
    viewModelScope.launch(exceptionHandler) {
      val hashedPassword = hashPassword(password) // Hash the password
      val response = ApiService.login(normalizedUsername, hashedPassword) // Make the API call

      when (response.code) {
        HttpStatusCode.OK.value -> { // Success
          // Extract the plain string JWT token from the response body
          val responseBody =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer) // Write the body to a buffer
                buffer.readUtf8() // Read the plain string token
              }


          if (!responseBody.isNullOrBlank()) {
            _username.value = username
            _isLoggedIn.value = true // Update the logged-in state
            _statusMessage.value = "Login successful"
            println("Login successful! username: $username")
            fetchInventory() // Fetch inventory after login
          } else {
            _statusMessage.value = "Failed to retrieve token from response."
          }
        }
        HttpStatusCode.Unauthorized.value -> { // Unauthorized
          _statusMessage.value = "Unauthorized: Invalid credentials."
          _isLoggedIn.value = false
        }
        else -> { // Other errors
          val errorMessage =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer) // Write the body to a buffer
                buffer.readUtf8() // Read the error message as plain text
              } ?: "Unknown error"
          _statusMessage.value = "HTTP error (${response.code}): $errorMessage"
          _isLoggedIn.value = false
        }
      }
    }
  }

  // Fetch the inventory of the logged-in user
  fun fetchInventory() {
    viewModelScope.launch(exceptionHandler) {
      val response = ApiService.getInventory() // Make the API call

      when (response.code) {
        HttpStatusCode.OK.value -> { // Success
          _inventory.value =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                Json.decodeFromString<List<CardModel>>(buffer.readUtf8()) // Deserialize JSON
              } ?: emptyList()
        }
        HttpStatusCode.Unauthorized.value -> { // Unauthorized
          _statusMessage.value = "Unauthorized: Please log in again."
          logout() // Log the user out
        }
        else -> { // Other errors
          val errorMessage =
              response.body?.let { body ->
                val buffer = Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
              } ?: "Unknown error"
          _statusMessage.value = "HTTP error (${response.code}): $errorMessage"
        }
      }
    }
  }

  // Logout function
  fun logout() {
    _inventory.value = emptyList()
    _isLoggedIn.value = false
    ApiService.logout() // Clear the token in ApiService
    _statusMessage.value = "Logged out successfully"
  }

  // Clear Status Message
  fun clearStatusMessage() {
    _statusMessage.value = ""
  }

  // Hash passwords
  @OptIn(ExperimentalEncodingApi::class)
  private suspend fun hashPassword(password: String): String {
    val provider = CryptographyProvider.Default
    val hasher: Hasher = provider.get(SHA512).hasher()
    val digest: ByteArray = hasher.hash(password.encodeToByteArray())
    return Base64.encode(digest)
  }

  private fun isPasswordValid(password: String): Boolean {
    return password.matches(PASSWORD_REGEX)
  }
}
