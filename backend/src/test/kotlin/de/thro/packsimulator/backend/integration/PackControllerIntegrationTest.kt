package de.thro.packsimulator.backend.integration

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import de.thro.packsimulator.backend.service.AccountService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import java.security.MessageDigest
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("integration")
class PackControllerIntegrationTest {

    private var logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var setRepository: SetRepository

    @Autowired
    private lateinit var accountService: AccountService

    private val restTemplate = RestTemplate()

    @BeforeEach
    fun setup() {
        val hashedPassword = hashPassword("testPassword")
        accountRepository.save(Account("testuser", hashedPassword))
        logger.info("setup: ${accountRepository.findAll().toList()}")
    }

    @AfterEach
    fun tearDown() {
        accountRepository.deleteAll()
        setRepository.deleteAll()
    }

    @Test
    fun `should open a pack and return cards`() {
        // Arrange
        val hashedPassword = hashPassword("testPassword")
        val token = accountService.login("testuser", hashedPassword)
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")
        val entity = HttpEntity(null, headers)

        // Act
        val response = restTemplate.exchange(
            "http://localhost:$port/api/packs/open?setId=A1",
            HttpMethod.POST,
            entity,
            object : ParameterizedTypeReference<List<Card>>() {}
        )

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertTrue(response.body!!.isNotEmpty())
    }
}

private fun hashPassword(password: String): String {
    val digest = MessageDigest.getInstance("SHA-512")
    val hash = digest.digest(password.toByteArray())
    return Base64.getEncoder().encodeToString(hash)
}
