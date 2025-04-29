package de.thro.packsimulator.viewmodel.set

import de.thro.packsimulator.data.set.SetBrief
import de.thro.packsimulator.manager.APIManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

// Time out in milliseconds
private const val TIME_OUT = 5000L

// Focus on a determined set of Card Sets for better understanding
private val testSets = listOf("A1", "A2a", "A2b")

object SetBriefViewModel {

    /*
    * Observable state for List of SetBrief.
    */
    private val _setBriefs = MutableStateFlow<List<SetBrief>>(emptyList())
    val setBriefs: StateFlow<List<SetBrief>> = _setBriefs

    /*
    * Fetches the card sets and updates the state.
    */
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchSetBriefs() {
        GlobalScope.launch {
            val fetchedSetBriefs = withTimeoutOrNull(TIME_OUT) {
                APIManager.searchSets()
            }
            if (fetchedSetBriefs != null) {
                val validSets = fetchedSetBriefs.filter { setBrief ->
                    setBrief.logo != null &&
                            setBrief.symbol != null &&
                            testSets.contains(setBrief.id)
                }
                _setBriefs.value = validSets
            }
        }
    }
}