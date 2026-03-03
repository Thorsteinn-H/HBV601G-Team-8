package `is`.hi.hbv601g.icelandicweatherapp.model


data class CurrentLocationWeather(
    val locationName: String,
    val temperature: Double?,
    val windSpeed: Double?,
    val precipitation: Double?
)
