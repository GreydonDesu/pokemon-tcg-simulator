package de.thro.packsimulator.backend.dto

data class SetDTO(
    val id: String,
    val name: String,
    val logo: String,
    val symbol: String,
    val cardCount: CardCountDTO,
    val releaseDate: String,
    val cards: List<CardDTO>
) {
    val totalCards: Int
        get() = cardCount.total
}

data class CardCountDTO(
    val total: Int
)

data class CardDTO(
    val id: String,
    val localId: String,
    val name: String,
    val image: String
)
