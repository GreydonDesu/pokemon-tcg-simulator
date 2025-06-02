package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.dto.SetBriefDTO
import de.thro.packsimulator.backend.dto.SetDTO
import de.thro.packsimulator.backend.repository.SetRepository
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@Service
class SetService(private val setRepository: SetRepository) {

    companion object {
        const val BUFFER_SIZE_MB = 10 // Buffer size in MB
        const val BYTES_PER_MB = 1024 * 1024 // Number of bytes in one MB
        const val IMAGE_DIRECTORY = "images" // Directory to save images
        const val BASE_URL = "http://localhost:8080/images" // Base URL to serve images
    }

    private val logger: Logger = LoggerFactory.getLogger(SetService::class.java)

    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://api.tcgdex.net/v2/en")
        .codecs { configurer ->
            configurer.defaultCodecs().maxInMemorySize(BUFFER_SIZE_MB * BYTES_PER_MB) // Use constants here
        }
        .build()

    private val imageDirectory: Path = Paths.get(IMAGE_DIRECTORY) // Use constant
    private val baseUrl = BASE_URL // Use constant

    init {
        Files.createDirectories(imageDirectory) // Ensure the directory exists
    }

    @PostConstruct
    fun initializeData() {
        logger.info("Initializing data on application startup...")
        fetchAndSaveSetsFromAPI()
        logger.info("Data initialization complete.")
    }

    fun getAllSets(): List<Set> {
        logger.info("Fetching all sets from the repository...")
        val sets = setRepository.findAll()
        logger.info("Fetched ${sets.size} sets from the repository.")
        return sets
    }

    private fun fetchAndSaveSetsFromAPI() {
        logger.info("Fetching sets from the external API...")

        val testSets = listOf("A1", "A2a", "A2b")

        val briefSets = webClient.get()
            .uri("/sets")
            .retrieve()
            .bodyToFlux(SetBriefDTO::class.java)
            .collectList()
            .block() ?: emptyList()

        val filteredBriefSets = briefSets.filter { it.id in testSets }

        val detailedSets = filteredBriefSets.mapNotNull { fetchSetDetails(it.id) }

        val backendSets = detailedSets.map { transformToSet(it) }

        logger.info("Clearing existing sets from the repository...")
        setRepository.deleteAll()

        logger.info("Saving ${backendSets.size} sets to the repository...")
        setRepository.saveAll(backendSets)

        logger.info("Sets have been successfully updated in the repository.")
    }

    private fun fetchSetDetails(id: String): SetDTO? {
        return webClient.get()
            .uri("/sets/$id")
            .retrieve()
            .bodyToMono(SetDTO::class.java)
            .block()
    }

    private fun transformToSet(setDTO: SetDTO): Set {
        val logoUrl = "${setDTO.logo}.png"
        val symbolUrl = setDTO.symbol?.let { "$it.png" }

        val logoPath = downloadAndSaveImage(logoUrl, "logo_${setDTO.id}.png")
        val symbolPath = symbolUrl?.let { downloadAndSaveImage(it, "symbol_${setDTO.id}.png") }

        logger.info("Resulting Logo Path: $logoPath")
        logger.info("Resulting Symbol Path: $symbolPath")

        val cards = setDTO.cards?.map {
            val cardImageUrl = "${it.image}/high.png"
            logger.info("Loading cards from $cardImageUrl")
            Card(
                id = it.id,
                localId = it.localId,
                name = it.name,
                image = downloadAndSaveImage(cardImageUrl, "card_${it.id}.png")
            )
        } ?: emptyList()

        return Set(
            id = setDTO.id,
            name = setDTO.name,
            logo = "$baseUrl/${Paths.get(logoPath).fileName}",
            symbol = symbolPath?.let { "$baseUrl/${Paths.get(it).fileName}" },
            totalCards = setDTO.totalCards,
            releaseDate = setDTO.releaseDate,
            cards = cards.map { card ->
                card.copy(image = "$baseUrl/${Paths.get(card.image).fileName}")
            }
        )
    }

    private fun downloadAndSaveImage(url: String, fileName: String): String? {
        val filePath = imageDirectory.resolve(fileName)
        return try {
            webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(org.springframework.core.io.buffer.DataBuffer::class.java)
                .let { dataBufferFlux ->
                    DataBufferUtils.write(dataBufferFlux, filePath, StandardOpenOption.CREATE)
                        .block()
                }
            filePath.toString()
        } catch (ex: IOException) {
            logger.error("Failed to download image from $url: ${ex.message}")
            null
        }
    }
}
