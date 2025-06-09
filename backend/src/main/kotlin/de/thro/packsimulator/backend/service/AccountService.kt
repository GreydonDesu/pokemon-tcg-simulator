package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.util.JwtUtil
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

  // Login (validate username and password, return JWT token)
  fun login(username: String, plainPassword: String): String {
    val account = accountRepository.findById(username).orElse(null)
    require(account != null) { "Invalid username or password" }

    // Compare the stored hashed password with the provided password
    require(passwordEncoder.matches(plainPassword, account.password)) {
      "Invalid username or password"
    }

    // Generate JWT token
    return JwtUtil.generateToken(username)
  }

  // Fetch the inventory of the currently authenticated user
  fun getInventory(token: String): List<Card> {
    // Extract the username from the JWT token
    val username = JwtUtil.extractUsername(token)
    require(username != null) { "Invalid or expired token" }

    // Retrieve the account from the repository
    val account = accountRepository.findById(username).orElse(null)
    require(account != null) { "User not found" }

    // Return the user's inventory
    return account.inventory
  }
}
