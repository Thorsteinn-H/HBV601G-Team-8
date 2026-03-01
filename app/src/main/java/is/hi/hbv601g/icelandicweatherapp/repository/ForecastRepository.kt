package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDao
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.network.MetApiClient
import `is`.hi.hbv601g.icelandicweatherapp.model.toForecastDtos

/**
 * Repo responsible for weather forecasts
 * - Fetches forecast data from the API
 * - converts API responses into ForecastDto
 * - Stores forecasts in Room
 * - Loads from Room for the UI
 */
class ForecastRepository(
    private val forecastDao: ForecastDao
) {
    /**
     * Fetch from met.no and save it to the database
     */
    suspend fun refreshForecast(
        latitude: Double,
        longitude: Double
    ){
        // raw forecasst data from the API
        val response = MetApiClient.api.getForecast(latitude,longitude)

        //convert API response to list fo ForecastDto
        val forecasts: List<ForecastDto> = response.toForecastDtos()

        forecastDao.clearForecasts()
        forecastDao.insertForecasts(forecasts)
    }

    /**
     * Load all forecsasts stored locally
     */
    suspend fun loadForecasts(): List<ForecastDto>{
        return forecastDao.getAllForecasts()
    }
}