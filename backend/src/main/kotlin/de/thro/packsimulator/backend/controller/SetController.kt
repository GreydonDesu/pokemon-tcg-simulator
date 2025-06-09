package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.service.SetService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/sets")
class SetController(private val setService: SetService) {

  @GetMapping fun getAllSets(): List<Set> = setService.getAllSets()
}
