package de.thro.packsimulator.backend.service

import de.thro.packsimulator.backend.data.Card
import de.thro.packsimulator.backend.data.Set
import de.thro.packsimulator.backend.dto.SetDTO
import de.thro.packsimulator.backend.repository.SetRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SetService(private val setRepository: SetRepository) {

    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://api.tcgdex.net/v2/en")
        .build()

    fun getAllSets(): List<Set> {
        // Check if the repository is empty
        if (setRepository.count() == 0L) {
            fetchAndSaveSetsFromAPI()
        }
        return setRepository.findAll()
    }

    private fun fetchAndSaveSetsFromAPI() {

        // Limit sets to the following
        val testSets = listOf("A1", "A2a", "A2b")

        val setsDTO = webClient.get()
            .uri("/sets")
            .retrieve()
            .bodyToFlux(SetDTO::class.java) // Assuming the public API returns a list
            .collectList()
            .block()

        if (setsDTO != null) {
            // Filter sets by IDs A1, A2a, A2b
            val filteredSetsDTO = setsDTO.filter { it.id in testSets }

            // Transform to backend Set model
            val filteredSets = filteredSetsDTO.map { transformToSet(it) }

            // Save filtered sets to the repository
            setRepository.saveAll(filteredSets)
        }
    }

    private fun transformToSet(setDTO: SetDTO): Set {
        return Set(
            id = setDTO.id,
            name = setDTO.name,
            logo = setDTO.logo,
            symbol = setDTO.symbol,
            totalCards = setDTO.totalCards,
            releaseDate = setDTO.releaseDate,
            cards = setDTO.cards.map { Card(it.id, it.localId, it.name, it.image) }
        )
    }
}
