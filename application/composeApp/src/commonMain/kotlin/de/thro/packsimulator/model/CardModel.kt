package de.thro.packsimulator.model

import kotlinx.serialization.Serializable

@Serializable
data class CardModel(val id: String, val localId: String, val name: String, val image: String)
