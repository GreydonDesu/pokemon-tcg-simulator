package de.thro.packsimulator.backend.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "accounts")
data class Account(
    @Id val username: String, // Username acts as the unique identifier
    val password: String, // Hashed password (use BCrypt for hashing)
    val inventory: MutableList<Card> = mutableListOf() // User inventory
)
