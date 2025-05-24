package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.User
import de.thro.packsimulator.backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun registerUser(username: String, password: String): User {
        val user = User(username = username, password = password)
        return userRepository.save(user)
    }

    fun getUser(username: String): User? {
        return userRepository.findByUsername(username)
    }
}
