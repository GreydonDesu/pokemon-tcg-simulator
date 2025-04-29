package de.thro.packsimulator.manager

import de.thro.packsimulator.data.set.Set
import de.thro.packsimulator.data.set.SetBrief
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// cURL of API
const val BASE_URL = "https://api.tcgdex.net/v2/en"
const val SETS_EXTENSION = "/sets"

object APIManager {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore unknown fields in the JSON response
            })
        }
    }

    // Make this a suspending function
    suspend fun searchSets(): List<SetBrief>? {
        return try {
            // Perform the HTTP request and return the result
            client.get(BASE_URL + SETS_EXTENSION).body()
        } catch (e: Exception) {
            println("Error fetching sets: ${e.message}")
            null
        }
    }

    suspend fun getSet(id: String): Set? {
        return try {
            // Perform the HTTP request and return the result
            client.get("$BASE_URL$SETS_EXTENSION/$id").body()
        } catch (e: Exception) {
            println("Error fetching set: ${e.message}")
            null
        }
    }
}
