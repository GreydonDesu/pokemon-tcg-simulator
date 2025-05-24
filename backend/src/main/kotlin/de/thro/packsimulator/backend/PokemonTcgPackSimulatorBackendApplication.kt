package de.thro.packsimulator.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokemonTcgPackSimulatorBackendApplication

fun main(args: Array<String>) {
	runApplication<PokemonTcgPackSimulatorBackendApplication>(*args)
}
