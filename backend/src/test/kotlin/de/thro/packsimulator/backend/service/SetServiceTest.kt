package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.dto.SetDTO
import de.thro.packsimulator.backend.repository.SetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
class SetServiceTest {

  @MockitoBean private lateinit var setRepository: SetRepository
  @Autowired private lateinit var setService: SetService

  @Test
  fun `should fetch all sets from the repository`() {
    val sets =
        listOf(Set("A1", "Genetic Apex", "logo.png", "symbol.png", 286, "2023-01-01", emptyList()))

    Mockito.`when`(setRepository.findAll()).thenReturn(sets)

    val result = setService.getAllSets()

    assertEquals(sets, result)
    Mockito.verify(setRepository, Mockito.times(1)).findAll()
  }

  @Test
  fun `should transform SetDTO to Set`() {
    val setDTO =
        SetDTO(
            id = "A1",
            name = "Genetic Apex",
            logo = "https://assets.tcgdex.net/en/tcgp/A1/logo",
            symbol = "https://assets.tcgdex.net/univ/tcgp/A1/symbol",
            cardCount = de.thro.packsimulator.backend.dto.CardCountDTO(10, 5, 5, 8, 2, 286),
            releaseDate = "2023-01-01",
            cards =
                listOf(
                    de.thro.packsimulator.backend.dto.CardDTO(
                        id = "A1-001",
                        localId = "001",
                        name = "Bulbasaur",
                        image = "https://assets.tcgdex.net/en/tcgp/A1/001")),
            serie = null,
            legal = null)

    val result = setService.transformToSet(setDTO)

    assertEquals("A1", result.id)
    assertEquals("Genetic Apex", result.name)
    assertEquals(286, result.totalCards)
    assertEquals("2023-01-01", result.releaseDate)
    assertEquals(1, result.cards.size)
    assertEquals("Bulbasaur", result.cards[0].name)
  }
}
