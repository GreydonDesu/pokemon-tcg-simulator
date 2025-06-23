package de.thro.packsimulator.backend.integration

import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.repository.SetRepository
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("integration")
class SetControllerIntegrationTest {

  @LocalServerPort private var port: Int = 0

  @Autowired private lateinit var setRepository: SetRepository

  private val restTemplate = RestTemplate()

  @AfterEach
  fun tearDown() {
    setRepository.deleteAll()
  }

  @Test
  fun `should fetch all sets`() {
    // Arrange
    val sets = listOf(Set("A1", "Test Set", "logo.png", null, 100, "2023-01-01", emptyList()))
    setRepository.saveAll(sets)

    val url = "http://localhost:$port/api/sets"
    val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }

    // Act
    val response = restTemplate.getForEntity<String>(url, headers)

    // Assert
    assertEquals(HttpStatus.OK, response.statusCode)
    assertEquals(1, setRepository.findAll().size)
  }
}
