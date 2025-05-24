package de.thro.packsimulator.backend.repository

import de.thro.packsimulator.backend.data.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}
