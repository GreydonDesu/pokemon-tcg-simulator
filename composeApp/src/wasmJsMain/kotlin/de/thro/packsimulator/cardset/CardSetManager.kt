package de.thro.packsimulator.cardset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.thro.packsimulator.cardset.data.CardSet
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CardSetManager {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore unknown fields in the JSON response
            })
        }
    }

    // Observable state for the card sets
    var cardSets by mutableStateOf<List<CardSet>>(emptyList())
        private set // Make the setter private to restrict external modifications

    // Fetches the card sets and updates the state
    fun fetchCardSets() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val url = "https://api.tcgdex.net/v2/en/sets"
                val fetchedCardSets: List<CardSet> = client.get(url).body()
                cardSets = fetchedCardSets
                println("Card sets fetched successfully: ${fetchedCardSets.size} sets loaded.")
            } catch (e: Exception) {
                println("Error fetching card sets: ${e.message}")
            }
        }
    }
}