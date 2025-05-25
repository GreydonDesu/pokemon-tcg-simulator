package de.thro.packsimulator.service

import de.thro.packsimulator.data.account.Account
import de.thro.packsimulator.data.card.CardBrief
import de.thro.packsimulator.data.set.Set
import de.thro.packsimulator.data.set.SetBrief
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

object APIService {
    private val client = KtorHttpClient.client
    private const val BASE_URL = "http://localhost:8080"

    // Fetch all sets (searchSets)
    suspend fun searchSets(): List<SetBrief>? {
        return try {
            client.get("$BASE_URL/api/sets").body()
        } catch (e: Exception) {
            println("Error fetching sets from backend: ${e.message}")
            null
        }
    }

    // Fetch a specific set by ID (getSet)
    suspend fun getSet(id: String): Set? {
        return try {
            client.get("$BASE_URL/api/sets/$id").body()
        } catch (e: Exception) {
            println("Error fetching set from backend: ${e.message}")
            null
        }
    }

    // Register an account (registerAccount)
    suspend fun registerAccount(account: Account): Account? {
        return try {
            client.post("$BASE_URL/api/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(account) // Send the Account object as JSON
            }.body()
        } catch (e: Exception) {
            println("Error registering account: ${e.message}")
            null
        }
    }

    // Fetch an account by username (getAccount)
    suspend fun getAccount(username: String): Account? {
        return try {
            client.get("$BASE_URL/api/auth/$username").body()
        } catch (e: Exception) {
            println("Error fetching account with username $username: ${e.message}")
            null
        }
    }

    // Open a pack (openPack)
    suspend fun openPack(userId: String, setId: String): List<CardBrief>? {
        return try {
            client.post("$BASE_URL/api/packs/open") {
                parameter("userId", userId)
                parameter("setId", setId)
            }.body()
        } catch (e: Exception) {
            println("Error opening pack for user $userId and set $setId: ${e.message}")
            null
        }
    }
}
