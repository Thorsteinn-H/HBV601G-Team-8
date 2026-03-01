package `is`.hi.hbv601g.icelandicweatherapp.model

/**
 * represents a geographic location in Iceland
 */
data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val region: String? = null
)
