package de.thro.packsimulator.cardset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.thro.packsimulator.cardset.data.SetBase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object CardSetManager {

    val baseUrl = "https://api.tcgdex.net/v2/en"
    val setsExtension = "/sets"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore unknown fields in the JSON response
            })
        }
    }

    // Observable state for the card sets
    var cardSets by mutableStateOf<List<SetBase>>(emptyList())
        private set // Make the setter private to restrict external modifications

    // Fetches the card sets and updates the state
    fun fetchCardSets() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val fetchedCardSets: List<SetBase> = client.get(baseUrl+ setsExtension).body()
                println("Card sets fetched successfully: ${fetchedCardSets.size} sets pulled.")
                for (cardSet in fetchedCardSets) {
                    if (cardSet.logo != null && cardSet.symbol != null) {
                        cardSets += cardSet
                    }
                }
                println("Valid card sets filtered successfully: ${cardSets.size}/${fetchedCardSets.size} sets pulled.")
            } catch (e: Exception) {
                println("Error fetching card sets: ${e.message}")
            }
        }
    }
}