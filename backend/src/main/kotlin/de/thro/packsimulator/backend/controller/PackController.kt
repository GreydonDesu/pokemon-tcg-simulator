package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.service.PackService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/packs")
class PackController(private val packService: PackService) {

  // Open a pack for the given user and set
  @PostMapping("/open")
  fun openPack(
      @RequestHeader("Authorization") token: String, // Get the token from the Authorization header
      @RequestParam setId: String
  ): ResponseEntity<List<Card>> {
    val jwt = token.removePrefix("Bearer ") // Remove the "Bearer " prefix
    val cards = packService.openPack(jwt, setId)
    return ResponseEntity.ok(cards)
  }
}
