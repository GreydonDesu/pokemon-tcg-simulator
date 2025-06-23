package de.thro.packsimulator.backend.integration

import de.thro.packsimulator.backend.repository.AccountRepository
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("integration")
class AccountControllerIntegrationTest {

  @Autowired private lateinit var mongoTemplate: MongoTemplate

  @LocalServerPort private var port: Int = 0

  @Autowired private lateinit var accountRepository: AccountRepository

  private val restTemplate = RestTemplate()

  @AfterEach
  fun tearDown() {
    accountRepository.deleteAll()
  }

  @Test
  fun `should connect to MongoDB`() {
    val db = mongoTemplate.db
    assertNotNull(db, "MongoDB connection should be established")
  }

  @Test
  fun `should register a new account`() {
    // Arrange
    val url = "http://localhost:$port/api/accounts/register?username=testUser&password=testPassword"

    // Act
    restTemplate.postForEntity<String>(url, null)

    // Check if the account is saved correctly
    val account = accountRepository.findById("testuser")
    assertEquals(true, account.isPresent)
    assertEquals("testuser", account.get().username)
  }

  @Test
  fun `should login with valid credentials`() {
    // Arrange
    val urlRegister = "http://localhost:$port/api/accounts/register?username=testUser&password=testPassword"
    restTemplate.postForEntity<String>(urlRegister, null)

    val urlLogin = "http://localhost:$port/api/accounts/login?username=testUser&password=testPassword"

    // Act
    val response = restTemplate.postForEntity<String>(urlLogin, null)

    // Assert
    assertEquals(200, response.statusCode.value())
    assertNotNull(response.body, "Response body should contain a token")
  }
}
