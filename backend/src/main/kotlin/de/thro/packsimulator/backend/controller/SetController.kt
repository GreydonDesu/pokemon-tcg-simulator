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
    // Path to the images directory
    private val imagesDirectory: Path = Paths.get("/app/images")

    @GetMapping
    fun getAllSets(): List<Set> = setService.getAllSets()

    @GetMapping("/images/{filename:.+}")
    fun serveImage(@PathVariable filename: String): ResponseEntity<Resource> {
        val filePath = imagesDirectory.resolve(filename).normalize()

        return try {
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists() && resource.isReadable) {
                println("Resource found: ${resource.filename}")
                ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (ex: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}
