package de.thro.packsimulator.backend.controller

import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.service.SetService
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/api/sets")
class SetController(private val setService: SetService) {

    @GetMapping
    fun getAllSets(): List<Set> = setService.getAllSets()
}
