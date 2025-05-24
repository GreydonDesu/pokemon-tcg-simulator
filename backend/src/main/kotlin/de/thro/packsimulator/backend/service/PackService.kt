package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.repository.SetRepository
import de.thro.packsimulator.backend.repository.UserRepository
import org.springframework.stereotype.Service

// Packs usually have 5 cards each
const val CARD_AMOUNT_PER_PACK = 5

@Service
class PackService(
    private val setRepository: SetRepository,
    private val userRepository: UserRepository
) {

    fun openPack(userId: String, setId: String): List<Card> {
        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val set = setRepository.findById(setId).orElseThrow { Exception("Set not found") }

        // Randomly select 5 cards
        val selectedCards = List(CARD_AMOUNT_PER_PACK) { set.cards.random() }

        // Update user's inventory
        user.inventory.addAll(selectedCards)
        userRepository.save(user)

        return selectedCards
    }
}
