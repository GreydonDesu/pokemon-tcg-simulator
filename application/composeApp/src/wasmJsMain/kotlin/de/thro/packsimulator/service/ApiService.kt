package de.thro.packsimulator.service

import de.thro.packsimulator.model.CardModel
import de.thro.packsimulator.model.SetModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiService {
  private const val BASE_URL: String = "http://localhost:8080"
  private var token: String? = null

  private val client =
    HttpClient(Js) {
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true // Allows parsing even if the JSON contains extra fields
            isLenient = true // Allows lenient parsing of JSON
          }
        )
      }
      install(Logging) { level = LogLevel.BODY }
    }

  // -------------------------
  // Account Management
  // -------------------------
  suspend fun registerAccount(username: String, password: String): String {
    return client
      .post("$BASE_URL/api/accounts/register") {
        contentType(ContentType.Application.Json)
        setBody(mapOf("username" to username, "password" to password))
      }
      .body() // Extract the response body as a String
  }

  suspend fun login(username: String, password: String): String {
    val response: String =
      client
        .post("$BASE_URL/api/accounts/login") {
          contentType(ContentType.Application.Json)
          setBody(mapOf("username" to username, "password" to password))
        }
        .body() // Extract the response body as a String
    token = response // Store the token after successful login
    return response
  }

  fun logout() {
    token = null // Clear the token locally
  }

  // -------------------------
  // Account Inventory Management
  // -------------------------
  suspend fun getInventory(): List<CardModel> {
    requireNotNull(token) {
      "User is not logged in. Token is missing."
    } // Ensure the user is logged in
    return client
      .get("$BASE_URL/api/accounts/inventory") {
        headers.append("Authorization", "Bearer $token") // Add the Authorization header
      }
      .body() // Extract the response body as a List<CardModel>
  }

  // -------------------------
  // Pack Management
  // -------------------------
  suspend fun openPack(setId: String): List<CardModel> {
    return client
      .post("$BASE_URL/api/packs/open") {
        headers.set("Authorization", "Bearer $token")
        parameter("setId", setId)
      }
      .body() // Extract the response body as a List<CardModel>
  }

  // -------------------------
  // Set Management
  // -------------------------
  suspend fun getAllSets(): List<SetModel> {
    return client.get("$BASE_URL/api/sets").body() // Extract the response body as a List<SetModel>
  }

  // -------------------------
  // Token Management
  // -------------------------
  fun setToken(newToken: String) {
    token = newToken
  }

  fun getToken(): String? {
    return token
  }
}
