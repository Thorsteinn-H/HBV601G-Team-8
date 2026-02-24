package `is`.hi.hbv601g.icelandicweatherapp.data

import com.google.gson.annotations.SerializedName

/**
 * Represents a SINGLE weather alert returned by Icelandic Met Office
 * DTO = Data transfer Object
 *
 * data mirrors the JSON structure
 * No business logic
 *
 *
 * Should we add possible values to each val??
 */
data class AlertDto(
    //English headline for the alert
    @SerializedName("headline_en")
    val headline: String?,
    // English description for the alert
    @SerializedName("description_en")
    val description: String?,
    val severity: String?,
    val urgency: String?
)
