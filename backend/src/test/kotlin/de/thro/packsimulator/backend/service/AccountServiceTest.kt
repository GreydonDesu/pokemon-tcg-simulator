package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.exception.InvalidCredentialsException
import de.thro.packsimulator.backend.exception.InvalidPasswordFormatException
import de.thro.packsimulator.backend.exception.InvalidTokenException
import de.thro.packsimulator.backend.exception.UserNotFoundException
import de.thro.packsimulator.backend.exception.UsernameAlreadyTakenException
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import de.thro.packsimulator.backend.util.JwtUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@EnableAutoConfiguration(
    exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
class AccountServiceTest {

  @MockitoBean private lateinit var accountRepository: AccountRepository
  @MockitoBean private lateinit var setRepository: SetRepository
  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var accountService: AccountService

  @Test
  fun `should register a new account successfully`() {
    val username = "testuser"
    val password = "validBase64Password=="

    // Mock repository behavior
    Mockito.`when`(accountRepository.existsById(username)).thenReturn(false)
    Mockito.`when`(accountRepository.save(Mockito.any(Account::class.java)))
        .thenReturn(Account(username, password))

    // Call the service method
    val account = accountService.register(username, password)

    // Assert the results
    assertEquals(username, account.username)
    assertEquals(password, account.password)
  }

  @Test
  fun `should throw UsernameAlreadyTakenException when username is already taken`() {
    val username = "testuser"
    val password = "validBase64Password=="

    // Mock repository behavior
    Mockito.`when`(accountRepository.existsById(username)).thenReturn(true)

    // Assert exception is thrown
    assertThrows<UsernameAlreadyTakenException> { accountService.register(username, password) }
  }

  @Test
  fun `should throw InvalidPasswordFormatException for invalid password format`() {
    val username = "testuser"
    val invalidPassword = "invalid!Base64Password" // Contains invalid characters for Base64

    // Assert exception is thrown
    val exception =
        assertThrows<InvalidPasswordFormatException> {
          accountService.register(username, invalidPassword)
        }

    // Assert the exception message
    assertEquals("Invalid password format", exception.message)
  }

  @Test
  fun `should login successfully and return a JWT token`() {
    val username = "testuser"
    val password = "validBase64Password=="
    val token = "jwtToken"

    // Mock repository and utility behavior
    Mockito.`when`(accountRepository.findById(username))
        .thenReturn(java.util.Optional.of(Account(username, password)))
    Mockito.`when`(jwtUtil.generateToken(username)).thenReturn(token)

    // Call the service method
    val result = accountService.login(username, password)

    // Assert the results
    assertEquals(token, result)
  }

  @Test
  fun `should throw InvalidCredentialsException for incorrect password`() {
    val username = "testuser"
    val password = "validBase64Password=="

    // Mock repository behavior
    Mockito.`when`(accountRepository.findById(username))
        .thenReturn(java.util.Optional.of(Account(username, "differentPassword")))

    // Assert exception is thrown
    assertThrows<InvalidCredentialsException> { accountService.login(username, password) }
  }

  @Test
  fun `should fetch inventory successfully`() {
    val token = "jwtToken"
    val username = "testuser"
    val inventory = mutableListOf(Card("1", "001", "Pikachu", "image.png"))

    // Mock utility and repository behavior
    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(username)
    Mockito.`when`(accountRepository.findById(username))
        .thenReturn(java.util.Optional.of(Account(username, "password", inventory)))

    // Call the service method
    val result = accountService.getInventory(token)

    // Assert the results
    assertEquals(inventory, result)
  }

  @Test
  fun `should throw InvalidTokenException for invalid token`() {
    val token = "invalidToken"

    // Mock utility behavior
    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(null)

    // Assert exception is thrown
    assertThrows<InvalidTokenException> { accountService.getInventory(token) }
  }

  @Test
  fun `should throw UserNotFoundException when user is not found`() {
    val token = "jwtToken"
    val username = "nonexistentUser"

    // Mock utility and repository behavior
    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(username)
    Mockito.`when`(accountRepository.findById(username)).thenReturn(java.util.Optional.empty())

    // Assert exception is thrown
    assertThrows<UserNotFoundException> { accountService.getInventory(token) }
  }

  @Test
  fun `should initialize the mocked classes correctly`() {
    assertNotNull(setRepository)
  }
}
