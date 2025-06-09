package de.thro.packsimulator.backend.controller

import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/images")
class ImageController {
  // Base directory where images are stored
  private val imageDirectory: Path = Paths.get("/META-INF/resources/images")

  private val logger: Logger = LoggerFactory.getLogger(ImageController::class.java)

  @GetMapping("/{fileName:.+}")
  fun getImage(@PathVariable fileName: String): ResponseEntity<Resource> {
    return try {
      // Resolve the file path
      val filePath: Path = imageDirectory.resolve(fileName).normalize()
      val resource: Resource = UrlResource(filePath.toUri())

      logger.info("Looking for $fileName in $filePath")

      // Check if the file exists and is readable
      if (!resource.exists() || !resource.isReadable) {
        logger.error("Resource $fileName could not be found")
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
      } else {
        logger.info("Resource $fileName found in $filePath - Resource $resource")
        // Return the file as a response
        ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"$fileName\"")
            .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
            .body(resource)
      }
    } catch (e: MalformedURLException) {
      ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
  }
}
