package de.thro.packsimulator.set.data

import kotlinx.serialization.Serializable

@Serializable
data class SetBrief(
    val id: String,
    val name: String,
    val logo: String? = null,   // Base URL for logo
    val symbol: String? = null, // Base URL for symbol
    val cardCount: CardCount
) {
    // Helper method to construct full URL for the logo
    fun getLogoUrl(extension: String = "png"): String {
        return "$logo.$extension"
    }

    // Helper method to construct full URL for the symbol
    fun getSymbolUrl(extension: String = "png"): String {
        return "$symbol.$extension"
    }
}

@Serializable
data class CardCount(
    val total: Int,
    val official: Int
)
