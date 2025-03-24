package de.thro.packsimulator.cardset.data

import kotlinx.serialization.Serializable

@Serializable
data class SetBase(
    val id: String,
    val name: String,
    val symbol: String? = null, // Base URL for symbol
    val logo: String? = null,   // Base URL for logo
    val cardCount: CardCount
) {
    // Helper method to construct full URL for the symbol
    fun getSymbolUrl(extension: String = "png"): String {
        return "$symbol.$extension"
    }

    // Helper method to construct full URL for the logo
    fun getLogoUrl(extension: String = "png"): String {
        return "$logo.$extension"
    }
}

@Serializable
data class CardCount(
    val total: Int,
    val official: Int
)
