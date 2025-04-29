package de.thro.packsimulator.viewmodel.set

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.thro.packsimulator.manager.APIManager
import de.thro.packsimulator.data.set.SetBrief
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

// Time out in seconds
const val TIME_OUT = 5000L
// Focus on a determined set of Card Sets for better understanding
val testSets = listOf("A1", "A2a", "A2b")

object SetBriefManager {
    /**
     * Observable state for the card sets
     */
    var setBriefs by mutableStateOf<List<SetBrief>>(emptyList())
        private set // Make the setter private to restrict external modifications

    /**
     * Fetches the card sets and updates the state
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchSetBriefs() {
        GlobalScope.launch {
            val fetchedSetBriefs = withTimeoutOrNull(TIME_OUT) {
                APIManager.searchSets()
            }

            if (fetchedSetBriefs != null) {
                for (setBrief in fetchedSetBriefs) {
                    if (setBrief.logo != null
                        && setBrief.symbol != null
                        && testSets.contains(setBrief.id)
                    ) {
                        setBriefs += setBrief
                    }
                }
                println("Valid sets filtered successfully: ${setBriefs.size}/${fetchedSetBriefs.size} sets pulled.")
            } else {
                println("Fetched Sets are null or timed out.")
            }
        }
    }
}