package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.service.SetService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SetController::class)
class SetControllerTest {

  @MockitoBean private lateinit var setService: SetService
  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var mockMvc: MockMvc

  @Test
  @WithMockUser(username = "testUser", roles = ["USER"])
  fun `should fetch all sets`() {
    val sets =
        listOf(
            Set(
                id = "A1",
                name = "Genetic Apex",
                logo = "https://assets.tcgdex.net/en/tcgp/A1/logo",
                symbol = "https://assets.tcgdex.net/univ/tcgp/A1/symbol",
                totalCards = 286,
                releaseDate = "2023-01-01",
                cards =
                    listOf(
                        Card(
                            "A1-001",
                            "001",
                            "Bulbasaur",
                            "https://assets.tcgdex.net/en/tcgp/A1/001"),
                        Card(
                            "A1-002",
                            "002",
                            "Ivysaur",
                            "https://assets.tcgdex.net/en/tcgp/A1/002"))))
    Mockito.`when`(setService.getAllSets()).thenReturn(sets)

    mockMvc
        .perform(
            get("/api/sets")
                .with(csrf()) // Add CSRF token if required
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk)
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value("A1"))
        .andExpect(jsonPath("$[0].name").value("Genetic Apex"))
        .andExpect(jsonPath("$[0].cards.length()").value(2))
        .andExpect(jsonPath("$[0].cards[0].id").value("A1-001"))
        .andExpect(jsonPath("$[0].cards[0].name").value("Bulbasaur"))
        .andExpect(jsonPath("$[0].cards[1].id").value("A1-002"))
        .andExpect(jsonPath("$[0].cards[1].name").value("Ivysaur"))
  }

  @Test
  fun `should initialize the mocked classes correctly`() {
    assertNotNull(jwtUtil)
  }
}
