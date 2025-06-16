package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Account
import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.repository.AccountRepository
import de.thro.packsimulator.backend.repository.SetRepository
import de.thro.packsimulator.backend.util.JwtUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
class PackServiceTest {

  @MockitoBean private lateinit var setRepository: SetRepository
  @MockitoBean private lateinit var accountRepository: AccountRepository
  @MockitoBean private lateinit var jwtUtil: JwtUtil
  @Autowired private lateinit var packService: PackService

  @Test
  fun `should open a pack successfully`() {
    val token = "jwtToken"
    val username = "testuser"
    val setId = "set1"
    val cards = listOf(Card("1", "001", "Pikachu", "image.png"))

    val set = Set(setId, "Set 1", "logo.png", null, 100, "2023-01-01", cards)
    val account = Account(username, "password", mutableListOf())

    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(username)
    Mockito.`when`(setRepository.findById(setId)).thenReturn(java.util.Optional.of(set))
    Mockito.`when`(accountRepository.findById(username)).thenReturn(java.util.Optional.of(account))

    val result = packService.openPack(token, setId)

    assertEquals(cards, result)
    assertTrue(account.inventory.containsAll(cards))
  }

  @Test
  fun `should throw IllegalArgumentException for invalid token`() {
    val token = "invalidToken"
    val setId = "set1"

    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(null)

    assertThrows<IllegalArgumentException> { packService.openPack(token, setId) }
  }

  @Test
  fun `should throw IllegalArgumentException for invalid set ID`() {
    val token = "jwtToken"
    val username = "testuser"
    val setId = "invalidSet"

    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(username)
    Mockito.`when`(setRepository.findById(setId)).thenReturn(java.util.Optional.empty())

    assertThrows<IllegalArgumentException> { packService.openPack(token, setId) }
  }

  @Test
  fun `should throw IllegalArgumentException for invalid user`() {
    val token = "jwtToken"
    val username = "testuser"
    val setId = "set1"

    val set = Set(setId, "Set 1", "logo.png", null, 100, "2023-01-01", emptyList())

    Mockito.`when`(jwtUtil.extractUsername(token)).thenReturn(username)
    Mockito.`when`(setRepository.findById(setId)).thenReturn(java.util.Optional.of(set))
    Mockito.`when`(accountRepository.findById(username)).thenReturn(java.util.Optional.empty())

    assertThrows<IllegalArgumentException> { packService.openPack(token, setId) }
  }
}
