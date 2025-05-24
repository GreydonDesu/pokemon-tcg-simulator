package de.thro.packsimulator.backend.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id val id: String? = null,
    val username: String,
    val password: String,
    val inventory: MutableList<Card> = mutableListOf()
)
