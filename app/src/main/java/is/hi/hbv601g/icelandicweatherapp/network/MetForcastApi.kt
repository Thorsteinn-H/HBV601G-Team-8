package `is`.hi.hbv601g.icelandicweatherapp.network


import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api interface for accessing weather forecast data from met.no
 */
interface MetForcastApi {

    /**
     * Retrieves the weather forecast for a giben lat and longitude
     * Returns a ForecastResponse object containing the full forecast
     */
    @GET("compact")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): ForecastResponse
}