package de.thro.packsimulator.data.account

import de.thro.packsimulator.data.card.CardBrief
import kotlinx.serialization.Serializable

/**
 * Account class
 * username     Account username
 * password     Account password (temporary)
 * inventory    Inventory of account
 */
@Serializable
data class Account(
    val username: String,
    val password: String,    // Temporary
    val inventory: List<CardBrief> = emptyList()
) {
    fun getInventory(): List<CardBrief> {
        return inventory
    }
}