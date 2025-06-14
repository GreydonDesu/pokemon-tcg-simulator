package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import de.thro.packsimulator.backend.util.JwtUtil
import kotlin.random.Random
import org.springframework.stereotype.Service

private const val CARD_AMOUNT = 5

@Service
class PackService(
    private val setRepository: SetRepository,
    private val accountRepository: AccountRepository,
    private val jwtUtil: JwtUtil // Inject JwtUtil as a dependency
) {

  // Open a pack and assign cards to the user's inventory
  fun openPack(token: String, setId: String): List<Card>? {
    // Validate the token and extract the username
    val username =
        jwtUtil.extractUsername(token) ?: throw IllegalArgumentException("Invalid or expired token")

    // Validate the set
    val set =
        setRepository.findById(setId).orElseThrow {
          IllegalArgumentException("Set with ID $setId not found")
        }

    // Validate the user
    val account =
        accountRepository.findById(username).orElseThrow {
          IllegalArgumentException("Account with username $username not found")
        }

    // Randomly select cards from the set (e.g., 5 cards per pack)
    val cardsToAdd: List<Card> = selectRandomCards(set.cards)

    // Add the selected cards to the user's inventory
    account.inventory.addAll(cardsToAdd)

    // Save the updated account
    accountRepository.save(account)

    return cardsToAdd
  }

  // Helper function to select random cards
  private fun selectRandomCards(cards: List<Card>): List<Card> {
    // If fewer or exactly `CARD_AMOUNT` cards are available, return all
    if (cards.size <= CARD_AMOUNT) {
      return cards
    }
    // Randomly select `CARD_AMOUNT` cards
    return cards.shuffled(Random).take(CARD_AMOUNT)
  }
}
