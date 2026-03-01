package `is`.hi.hbv601g.icelandicweatherapp.network


import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MetForcastApi {

    @GET("compact")
    suspend fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): ForecastResponse
}