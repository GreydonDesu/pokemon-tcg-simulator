package de.thro.packsimulator.set

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.thro.packsimulator.set.data.SetBrief
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

// cURL of API
const val baseUrl = "https://api.tcgdex.net/v2/en"
const val setsExtension = "/sets"

// Focus on a determined set of Card Sets for better understanding
val testSets = listOf("A1", "A2a", "A2b")

object SetBriefManager {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Ignore unknown fields in the JSON response
            })
        }
    }

    /**
     * Observable state for the card sets
     */
    var setBriefs by mutableStateOf<List<SetBrief>>(emptyList())
        private set // Make the setter private to restrict external modifications

    /**
     * Fetches the card sets and updates the state
     */
    fun fetchSetBriefs() {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val fetchedSetBriefs: List<SetBrief> = client.get(baseUrl + setsExtension).body()
                println("Sets fetched successfully: ${fetchedSetBriefs.size} sets pulled.")
                for (setBrief in fetchedSetBriefs) {
                    if (setBrief.logo != null
                        && setBrief.symbol != null
                        && testSets.contains(setBrief.id)
                    ) {
                        setBriefs += setBrief
                    }
                }
                println("Valid sets filtered successfully: ${setBriefs.size}/${fetchedSetBriefs.size} sets pulled.")
            } catch (e: NullPointerException) {
                println("Error fetching sets: ${e.message}")
            }
        }
    }
}