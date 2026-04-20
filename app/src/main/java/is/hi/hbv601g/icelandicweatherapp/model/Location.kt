package `is`.hi.hbv601g.icelandicweatherapp.model

/**
 * represents a geographic location in Iceland
 */
data class Location(
    val name: String, // name of the location
    val latitude: Double, // latitude coordinate
    val longitude: Double, // longitude coordinate
    val region: String? = null // region name(capital region, norht iceland)
)
