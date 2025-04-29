package de.thro.packsimulator.data.card

import kotlinx.serialization.Serializable

/**
 * Data class is based on the GraphQL entry
 * https://tcgdex.dev/reference/card-brief
 * id       The unique ID of the card
 * localId  Card Local ID
 * name     Card Name
 * image    Card Image (see Assets)
 */
@Serializable
data class CardBrief(
    val id: String,
    val localId: String,
    val name: String,
    val image: String? = null
){
    /**
     * Sets file extension for Image URL
     * 'high' is the default quality
     * 'png' is the default extension
     */
    fun getImageUrl(quality: String = "high", extension: String = "png"): String {
        return "$image/$quality.$extension"
    }
}