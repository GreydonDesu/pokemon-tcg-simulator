package de.thro.packsimulator.backend.dto

data class SetDTO(
    val id: String,
    val name: String,
    val logo: String?,
    val symbol: String?,
    val cardCount: CardCountDTO,
    val releaseDate: String?, // Nullable to handle missing releaseDate
    val cards: List<CardDTO>?, // Nullable to handle missing or null values
    val serie: SerieDTO?, // Nullable to handle missing series
    val legal: LegalDTO? // Nullable to handle missing legality information
) {
    val totalCards: Int
        get() = cardCount.total

    val safeCards: List<CardDTO>
        get() = cards ?: emptyList() // Use an empty list if cards is null
}

data class CardCountDTO(
    val firstEd: Int,
    val holo: Int,
    val normal: Int,
    val official: Int,
    val reverse: Int,
    val total: Int
)

data class CardDTO(
    val id: String,
    val localId: String,
    val name: String,
    val image: String
)

data class SerieDTO(
    val id: String,
    val name: String
)

data class LegalDTO(
    val expanded: Boolean,
    val standard: Boolean
)

