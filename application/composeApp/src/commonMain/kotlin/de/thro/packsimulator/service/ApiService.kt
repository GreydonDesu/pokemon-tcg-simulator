package de.thro.packsimulator.service

import coil3.network.NetworkResponse
import coil3.network.NetworkResponseBody
import de.thro.packsimulator.model.CardModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import okio.Buffer
import okio.BufferedSink
import okio.FileSystem
import okio.Path

object ApiService {
  private const val BASE_URL: String = "http://localhost:8080"
  private var token: String? = null // Store the token

  private val client =
      HttpClient(Js) {
        install(ContentNegotiation) {
          json(
              Json {
                ignoreUnknownKeys = true
                isLenient = true
              })
        }
        install(Logging) { level = LogLevel.BODY }
      }

  // -------------------------
  // Account Management
  // -------------------------
  suspend fun registerAccount(username: String, password: String): NetworkResponse {
    val response =
        client.post("$BASE_URL/api/accounts/register") {
          parameter("username", username) // Add username as a query parameter
          parameter("password", password) // Add password as a query parameter
        }
    return handleResponse(response)
  }

  suspend fun login(username: String, password: String): NetworkResponse {
    val response = client.post("$BASE_URL/api/accounts/login") {
      parameter("username", username)
      parameter("password", password)
    }

    return if (response.status == HttpStatusCode.OK) {
      // Read the plain string token from the response body
      val token = response.bodyAsText() // Correctly read the response as plain text
      setToken(token) // Store the token
      NetworkResponse(
        code = response.status.value,
        body = token.toNetworkResponseBody() // Wrap the plain string in a NetworkResponseBody
      )
    } else {
      handleResponse(response) // Handle error responses as usual
    }
  }

  fun logout() {
    token = null
  }

  // -------------------------
  // Account Inventory Management
  // -------------------------
  suspend fun getInventory(): NetworkResponse {
    val response = makeAuthenticatedRequest { headers ->
      client.get("$BASE_URL/api/accounts/inventory") {
        headers.forEach { (key, value) -> this.headers.append(key, value) }
      }
    }
    return handleResponse(response)
  }

  // -------------------------
  // Pack Management
  // -------------------------
  suspend fun openPack(setId: String): Result<List<CardModel>> {
    return try {
      val response = makeAuthenticatedRequest { headers ->
        client.post("$BASE_URL/api/packs/open") {
          headers.forEach { (key, value) -> this.headers.append(key, value) }
          parameter("setId", setId)
        }
      }

      if (response.status == HttpStatusCode.OK) {
        val cards = response.body<List<CardModel>>()
        Result.success(cards)
      } else {
        Result.failure(Exception("HTTP error (${response.status.value}): ${response.bodyAsText()}"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  // -------------------------
  // Set Management
  // -------------------------
  suspend fun getAllSets(): NetworkResponse {
    val response = client.get("$BASE_URL/api/sets")
    return handleResponse(response) // Returns a JSON string representing List<Set>
  }

  // -------------------------
  // Token Management
  // -------------------------
  // Function to set the token (e.g., after login)
  fun setToken(newToken: String) {
    println("Setting token: $newToken") // Debugging log
    token = newToken
  }

  // -------------------------
  // Helper functions
  // -------------------------
  private suspend fun makeAuthenticatedRequest(
    apiCall: suspend (headers: Map<String, String>) -> HttpResponse
  ): HttpResponse {
    val currentToken = token ?: throw UnsupportedOperationException("Please log in to continue")
    val headers = mapOf("Authorization" to "Bearer $currentToken")

    println("Sending Authorization header: Bearer $currentToken") // Debugging log

    return apiCall(headers)
  }

  private suspend fun handleResponse(response: HttpResponse): NetworkResponse {
    return try {
      if (response.status in HttpStatusCode.OK..HttpStatusCode.PartialContent) {
        val body = response.bodyAsText()
        NetworkResponse(code = response.status.value, body = body.toNetworkResponseBody())
      } else {
        val errorBody = response.bodyAsText()
        println("Error response: ${response.status.value} - $errorBody")
        NetworkResponse(code = response.status.value, body = errorBody.toNetworkResponseBody())
      }
    } catch (e: IOException) {
      println("IOException during response handling: ${e.message}")
      NetworkResponse(code = HttpStatusCode.ServiceUnavailable.value, body = null)
    } catch (e: Exception) {
      println("Exception during response handling: ${e.message}")
      NetworkResponse(code = HttpStatusCode.InternalServerError.value, body = null)
    }
  }

  private fun String.toNetworkResponseBody(): NetworkResponseBody {
    val buffer = Buffer().writeUtf8(this)
    return object : NetworkResponseBody {
      override suspend fun writeTo(sink: BufferedSink) {
        sink.writeAll(buffer)
      }

      override suspend fun writeTo(fileSystem: FileSystem, path: Path) {
        fileSystem.write(path) { writeAll(buffer) }
      }

      override fun close() {
        buffer.close()
      }
    }
  }
}
