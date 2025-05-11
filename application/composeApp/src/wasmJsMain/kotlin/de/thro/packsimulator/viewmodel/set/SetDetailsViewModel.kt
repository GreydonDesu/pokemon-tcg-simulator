package de.thro.packsimulator.viewmodel.set

import de.thro.packsimulator.data.account.Account
import de.thro.packsimulator.data.card.CardBrief
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Packs usually have 5 cards each
const val CARD_AMOUNT_PER_PACK = 5

object SetDetailsViewModel {

    /*
    * Randomly picks the specified number of cards from the given list.
    * Duplicates are allowed.
    */
    private fun pickRandomCards(cards: List<CardBrief>, count: Int): List<CardBrief> {
        return List(count) { cards.random() }
    }

    /*
    * Adds the selected cards to the account's inventory.
    */
    private fun addCardsToInventory(account: Account, cards: List<CardBrief>) {
        val updatedInventory = account.inventory.toMutableList().apply {
            addAll(cards)
        }
        account.inventory = updatedInventory
    }

    /*
    * Handles the logic for opening a pack.
    * Picks random cards, updates the account inventory, and calls a callback with the result.
    */
    fun openPack(
        account: Account?,
        cards: List<CardBrief>,
        scaffoldState: androidx.compose.material.ScaffoldState,
        scope: CoroutineScope
    ) {
        if (account == null) {
            // Show snackbar for logged-out state
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    "Please log in to open a pack."
                )
            }
            return
        }

        //Pick 5random cards
        val selectedCards = pickRandomCards(cards, CARD_AMOUNT_PER_PACK)

        //Add cards to account inventory
        addCardsToInventory(account, selectedCards)

        //Show snackbar with unpacked card names
        val cardNames = selectedCards.joinToString(", ") { it.name }
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "You unpacked the following cards: $cardNames"
            )
        }
    }
}
