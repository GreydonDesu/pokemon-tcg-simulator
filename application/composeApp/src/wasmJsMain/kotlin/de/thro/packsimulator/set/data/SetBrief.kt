package de.thro.packsimulator.set.data

import kotlinx.serialization.Serializable

/**
 * Data class is based on the GraphQL entry
 * https://tcgdex.dev/reference/set-brief
 * id                   Set Unique ID
 * name                 Set Name
 * logo                 Set logo (you can add .(webp|png|jpg) to customize the format)
 * symbol               Set Symbol (you can add .(webp|png|jpg) to customize the format
 * cardCount            Contain information about the number of cards in the set
 * cardCount.total      The total amount of cards in this set (including hidden)
 * cardCount.official   The amount of cards in this set (displayed on the bottom left/right of the card)
 */
@Serializable
data class SetBrief(
    val id: String,
    val name: String,
    val logo: String? = null,
    val symbol: String? = null,
    val cardCount: CardCount
) {
    /**
     * Sets file extension for Logo URL
     * 'png' is the default extension
     */
    fun getLogoUrl(extension: String = "png"): String {
        return "$logo.$extension"
    }

    /**
     * Sets file extension for Symbol URL
     * 'png' is the default extension
     */
    fun getSymbolUrl(extension: String = "png"): String {
        return "$symbol.$extension"
    }
}

@Serializable
data class CardCount(
    val total: Int,
    val official: Int
)
