package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.GlacierForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GlacierForecastApi {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("elevation") elevation: Int,
        @Query("current") current: String = "temperature_2m,wind_speed_10m,weather_code",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,precipitation_sum,snowfall_sum",
        @Query("forecast_days") forecastDays: Int = 3,
        @Query("timezone") timezone: String = "auto",
        @Query("wind_speed_unit") windSpeedUnit: String = "ms"
    ): GlacierForecastResponse
}