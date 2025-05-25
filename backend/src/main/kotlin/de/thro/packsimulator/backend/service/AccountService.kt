package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.repository.AccountRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService(private val accountRepository: AccountRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    // Register a new account (with hashed password)
    fun registerAccount(username: String, plainPassword: String): Account {
        require(!accountRepository.existsById(username)) { "Username $username is already taken" }
        val hashedPassword = passwordEncoder.encode(plainPassword)
        val account = Account(username = username, password = hashedPassword)
        return accountRepository.save(account)
    }

    // Login (validate username and password)
    fun login(username: String, plainPassword: String): Boolean {
        val account = accountRepository.findById(username).orElse(null)
        require(account != null) { "Invalid username or password" }

        // Compare the stored hashed password with the provided password
        require(passwordEncoder.matches(plainPassword, account.password)) { "Invalid username or password" }

        return true // Login successful
    }
}
