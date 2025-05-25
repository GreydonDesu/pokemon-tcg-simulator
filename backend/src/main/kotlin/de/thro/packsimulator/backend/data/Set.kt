package de.thro.packsimulator.backend.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sets")
data class Set(
    @Id val id: String, // Public API's "id"
    val name: String,   // Public API's "name"
    val logo: String,   // Public API's "logo"
    val symbol: String, // Public API's "symbol"
    val totalCards: Int, // Public API's "cardCount.total"
    val releaseDate: String, // Public API's "releaseDate"
    val cards: List<Card> // List of card details
)

data class Card(
    val id: String,      // Public API's "id"
    val localId: String, // Public API's "localId"
    val name: String,    // Public API's "name"
    val image: String    // Public API's "image"
)
