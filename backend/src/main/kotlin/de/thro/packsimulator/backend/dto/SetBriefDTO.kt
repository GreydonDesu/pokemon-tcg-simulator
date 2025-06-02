package de.thro.packsimulator.backend.dto

data class SetBriefDTO(
    val id: String,
    val name: String,
    val logo: String?,      // Optional, as not all sets have the logo field
    val symbol: String?,    // Optional, as not all sets have the symbol field
    val cardCount: CardCountBriefDTO
)

data class CardCountBriefDTO(
    val total: Int,
    val official: Int
)
