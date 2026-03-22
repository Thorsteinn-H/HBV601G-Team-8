package `is`.hi.hbv601g.icelandicweatherapp.model

import kotlin.math.exp

object WeatherUtils {
    /**
     * Calculates the apparent temperature ("feels like") based on the formula:
     * AT = Ta + 0.33 * e - 0.7 * v - 4.00
     *
     * where:
     * Ta = air temperature (°C)
     * e = water vapour pressure (hPa)
     * v = wind speed (m/s)
     *
     * Vapour pressure e is calculated as:
     * e = (RH / 100) * 6.105 * exp((17.27 * Ta) / (237.7 + Ta))
     *
     * @param temperature Dry-bulb temperature in Celsius
     * @param windSpeed Wind speed in m/s
     * @param relativeHumidity Relative humidity in %
     * @return Apparent temperature in Celsius, or null if any input is null
     */
    fun calculateFeelsLike(
        temperature: Double?,
        windSpeed: Double?,
        relativeHumidity: Double?
    ): Double? {
        if (temperature == null || windSpeed == null || relativeHumidity == null) {
            return null
        }

        val e = (relativeHumidity / 100.0) * 6.105 * exp((17.27 * temperature) / (237.7 + temperature))
        return temperature + (0.33 * e) - (0.7 * windSpeed) - 4.00
    }
}
