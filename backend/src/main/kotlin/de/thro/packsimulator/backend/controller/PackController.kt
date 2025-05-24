package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.service.PackService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/packs")
class PackController(private val packService: PackService) {

    @PostMapping("/open")
    fun openPack(@RequestParam userId: String, @RequestParam setId: String): List<Card> {
        return packService.openPack(userId, setId)
    }
}
