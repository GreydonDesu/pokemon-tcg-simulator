package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.service.PackService
import de.thro.packsimulator.backend.util.JwtUtil
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PackController::class)
class PackControllerTest {

  @MockitoBean private lateinit var packService: PackService
  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  @WithMockUser(username = "testUser", roles = ["USER"])
  fun `should open a pack and return cards`() {
    val token = "mocked-jwt-token"
    val setId = "A1"
    val cards =
        listOf(
            Card("A1-001", "001", "Bulbasaur", "https://assets.tcgdex.net/en/tcgp/A1/001"),
            Card("A1-002", "002", "Ivysaur", "https://assets.tcgdex.net/en/tcgp/A1/002"))
    Mockito.`when`(packService.openPack(token, setId)).thenReturn(cards)

    mockMvc
        .perform(
            post("/api/packs/open")
                .header("Authorization", "Bearer $token")
                .param("setId", setId)
                .with(csrf()) // Add CSRF token if required
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value("A1-001"))
        .andExpect(jsonPath("$[0].name").value("Bulbasaur"))
        .andExpect(jsonPath("$[1].id").value("A1-002"))
        .andExpect(jsonPath("$[1].name").value("Ivysaur"))
  }

  @Test
  fun `should initialize the mocked classes correctly`() {
    assertNotNull(jwtUtil)
  }
}
