package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.config.SecurityConfig
import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.service.AccountService
import de.thro.packsimulator.backend.util.JwtUtil
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AccountController::class)
@Import(SecurityConfig::class)
class AccountControllerTest {

  @MockitoBean private lateinit var accountService: AccountService
  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  fun `should register a new account`() {
    // Mock the behavior of accountService.register()
    val mockAccount =
        Account(
            username = "testUser",
            password = "hashedPassword", // Simulate a hashed password
            inventory = mutableListOf() // Empty inventory for a new account
            )
    Mockito.`when`(accountService.register("testUser", "testPassword")).thenReturn(mockAccount)

    mockMvc
        .perform(
            post("/api/accounts/register")
                .param("username", "testUser")
                .param("password", "testPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(
            content()
                .string("Account registered successfully. Please login with the new credentials."))
  }

  @Test
  fun `should login and return JWT token`() {
    val token = "mocked-jwt-token"
    Mockito.`when`(accountService.login("testUser", "testPassword")).thenReturn(token)

    mockMvc
        .perform(
            post("/api/accounts/login")
                .param("username", "testUser")
                .param("password", "testPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(content().string(token))
  }

  @Test
  fun `should return bad request for empty username or password`() {
    mockMvc
        .perform(
            post("/api/accounts/login")
                .param("username", "")
                .param("password", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest)
        .andExpect(content().string("Username and password must not be empty"))
  }

  @Test
  @WithMockUser(username = "testUser", roles = ["USER"])
  fun `should fetch inventory for authenticated user`() {
    val token = "mocked-jwt-token"
    val inventory =
        listOf(
            Card("A1-001", "001", "Bulbasaur", "https://assets.tcgdex.net/en/tcgp/A1/001"),
            Card("A1-002", "002", "Ivysaur", "https://assets.tcgdex.net/en/tcgp/A1/002"))
    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn("testUser")
    Mockito.`when`(accountService.getInventory(token)).thenReturn(inventory)

    mockMvc
        .perform(
            get("/api/accounts/inventory")
                .header("Authorization", "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value("A1-001"))
        .andExpect(jsonPath("$[0].name").value("Bulbasaur"))
        .andExpect(jsonPath("$[1].id").value("A1-002"))
        .andExpect(jsonPath("$[1].name").value("Ivysaur"))
  }
}
