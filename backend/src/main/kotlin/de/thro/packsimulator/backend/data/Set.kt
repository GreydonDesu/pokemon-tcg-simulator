package de.thro.packsimulator.backend.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "sets")
data class Set(
    @Id val id: String? = null,
    val name: String,
    val releaseDate: String,
    val cards: List<Card>
)
