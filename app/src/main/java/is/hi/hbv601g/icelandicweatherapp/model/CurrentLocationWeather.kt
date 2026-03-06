package `is`.hi.hbv601g.icelandicweatherapp.model

/**
 * represents the current weather information for a specific location
 */
data class CurrentLocationWeather(
    val locationName: String, // name of the location
    val temperature: Double?,  // current temp in Celsius by default
    val windSpeed: Double?, // windspeed in meter per second
    val precipitation: Double? // precipitation in millimeters
)
