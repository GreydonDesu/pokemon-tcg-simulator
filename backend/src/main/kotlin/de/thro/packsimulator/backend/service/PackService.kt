package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

private const val CARD_AMOUNT = 5

@Service
class PackService(
    private val setRepository: SetRepository,
    private val accountRepository: AccountRepository
) {

    // Open a pack and assign cards to the user's inventory
    fun openPack(username: String, setId: String): List<Card> {
        // Validate the set
        val set = setRepository.findById(setId)
            .orElseThrow { IllegalArgumentException("Set with ID $setId not found") }

        // Validate the user
        val account = accountRepository.findById(username)
            .orElseThrow { IllegalArgumentException("Account with username $username not found") }

        // Randomly select cards from the set (e.g., 5 cards per pack)
        val cardsToAdd = selectRandomCards(set.cards)

        // Add the selected cards to the user's inventory
        account.inventory.addAll(cardsToAdd)

        // Save the updated account
        accountRepository.save(account)

        return cardsToAdd
    }

    // Helper function to select random cards
    private fun selectRandomCards(cards: List<Card>): List<Card> {
        if (cards.size <= CARD_AMOUNT) {
            return cards // If fewer cards are available, return all
        }
        return cards.shuffled(Random).take(CARD_AMOUNT) // Randomly select `count` cards
    }
}
