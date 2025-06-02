package de.thro.packsimulator.model

import kotlinx.serialization.Serializable

@Serializable
data class SetModel(
    val id: String,
    val name: String,
    val logo: String?,
    val symbol: String?,
    val totalCards: Int,
    val releaseDate: String,
    val cards: List<CardModel>
)
