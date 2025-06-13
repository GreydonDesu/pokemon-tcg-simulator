package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.exception.InvalidCredentialsException
import de.thro.packsimulator.backend.exception.InvalidPasswordFormatException
import de.thro.packsimulator.backend.exception.InvalidTokenException
import de.thro.packsimulator.backend.exception.UserNotFoundException
import de.thro.packsimulator.backend.exception.UsernameAlreadyTakenException
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.util.JwtUtil
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URLDecoder

private val BASE64_REGEX = Regex("^[A-Za-z0-9+/=]+\$")

@Service
class AccountService(
  private val accountRepository: AccountRepository,
  private val jwtUtil: JwtUtil // Inject JwtUtil as a dependency
) {

  private val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

  // Register a new account
  @Throws(InvalidPasswordFormatException::class)
  fun register(username: String, clientHashedPassword: String): Account {
    // Normalize username
    val normalizedUsername = username.lowercase()
    logger.info("Attempting to register account for username: $normalizedUsername")

    // Validate the Base64 format
    validatePasswordFormat(clientHashedPassword)
    logger.debug("Password format validated for username: $normalizedUsername")

    // Check if the username is already taken
    if (accountRepository.existsById(normalizedUsername)) {
      logger.warn("Registration failed: Username $normalizedUsername is already taken")
      throw UsernameAlreadyTakenException("Username $username is already taken")
    }

    // Store the SHA-512 hash directly in the database
    logger.debug("Storing hashed password: $clientHashedPassword")
    val account = Account(username = normalizedUsername, password = clientHashedPassword)
    val savedAccount = accountRepository.save(account)
    logger.info("Account successfully registered for username: $normalizedUsername")
    return savedAccount
  }

  // Login and return JWT token
  fun login(username: String, clientHashedPassword: String): String {
    // Normalize username
    val normalizedUsername = username.lowercase()
    logger.info("Attempting login for username: $normalizedUsername")

    // Validate the Base64 format
    validatePasswordFormat(clientHashedPassword)
    logger.debug("Password format validated for username: $normalizedUsername")

    // Retrieve the account from the database
    val account = accountRepository.findById(normalizedUsername).orElse(null)
      ?: run {
        logger.warn("Login failed: User not found for username: $normalizedUsername")
        throw InvalidCredentialsException("Invalid username or password")
      }

    // Compare the SHA-512 hash received from the frontend with the stored hash
    val decodedPassword = URLDecoder.decode(clientHashedPassword, "UTF-8")
    logger.debug("Stored password: ${account.password}, Received password: $decodedPassword")
    if (account.password != decodedPassword) {
      logger.warn("Login failed: Incorrect password for username: $normalizedUsername")
      throw InvalidCredentialsException("Invalid username or password")
    }

    // Generate and return a JWT token
    val token = jwtUtil.generateToken(normalizedUsername) // Use the injected JwtUtil
    logger.info("Login successful for username: $normalizedUsername")
    return token
  }

  // Fetch the inventory of the currently authenticated user
  fun getInventory(token: String): List<Card> {
    logger.info("Fetching inventory for token: $token")

    // Extract the username from the JWT token
    val username = validateTokenAndGetUsername(token)
    logger.debug("Token validated and username extracted: $username")

    // Retrieve the account from the repository
    val account = accountRepository.findById(username).orElse(null)
      ?: run {
        logger.warn("Inventory fetch failed: User not found for username: $username")
        throw UserNotFoundException("User not found")
      }

    // Return the user's inventory
    logger.info("Inventory successfully fetched for username: $username")
    return account.inventory
  }

  // Private helper methods
  private fun validatePasswordFormat(clientHashedPassword: String) {
    logger.debug("Validating password format: $clientHashedPassword")
    if (clientHashedPassword.isBlank() || !BASE64_REGEX.matches(clientHashedPassword)) {
      logger.warn("Password validation failed: Invalid password format")
      throw InvalidPasswordFormatException("Invalid password format")
    }
    logger.debug("Password format is valid")
  }

  private fun validateTokenAndGetUsername(token: String): String {
    val username = jwtUtil.extractUsername(token) // Use the injected JwtUtil
    if (username == null) {
      logger.warn("Token validation failed: Invalid or expired token")
      throw InvalidTokenException("Invalid or expired token")
    }
    logger.debug("Token successfully validated and username extracted: $username")
    return username
  }
}
