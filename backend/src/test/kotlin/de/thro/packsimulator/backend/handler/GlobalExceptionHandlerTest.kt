package de.thro.packsimulator.backend.handler

import de.thro.packsimulator.backend.util.JwtUtil
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [MockController::class]) // Include MockController
@Import(GlobalExceptionHandler::class) // Import the exception handler
@AutoConfigureMockMvc(addFilters = false) // Disable Spring Security filters
class GlobalExceptionHandlerTest {

  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  fun `should handle InvalidPasswordFormatException`() {
    mockMvc
        .perform(get("/mock/invalid-password-format"))
        .andExpect(status().isBadRequest)
        .andExpect(jsonPath("$.error").value("Invalid password format"))
  }

  @Test
  fun `should handle UsernameAlreadyTakenException`() {
    mockMvc
        .perform(get("/mock/username-already-taken"))
        .andExpect(status().isConflict)
        .andExpect(jsonPath("$.error").value("Username is already taken"))
  }

  @Test
  fun `should handle InvalidCredentialsException`() {
    mockMvc
        .perform(get("/mock/invalid-credentials"))
        .andExpect(status().isUnauthorized)
        .andExpect(jsonPath("$.error").value("Invalid username or password"))
  }

  @Test
  fun `should handle InvalidTokenException`() {
    mockMvc
        .perform(get("/mock/invalid-token"))
        .andExpect(status().isUnauthorized)
        .andExpect(jsonPath("$.error").value("Invalid or expired token"))
  }

  @Test
  fun `should handle UserNotFoundException`() {
    mockMvc
        .perform(get("/mock/user-not-found"))
        .andExpect(status().isNotFound)
        .andExpect(jsonPath("$.error").value("User not found"))
  }

  @Test
  fun `should handle generic Exception`() {
    mockMvc
        .perform(get("/mock/generic-exception"))
        .andExpect(status().isInternalServerError)
        .andExpect(jsonPath("$.error").value("An unexpected error occurred"))
  }

    @Test
    fun `should initialize the mocked classes correctly`() {
        assertNotNull(jwtUtil)
    }
}
