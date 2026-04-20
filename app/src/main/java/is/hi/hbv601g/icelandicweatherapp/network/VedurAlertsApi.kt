package `is`.hi.hbv601g.icelandicweatherapp.network

//import androidx.room.Query fyrir database ekki API köll
import retrofit2.http.Query
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import retrofit2.Response
import retrofit2.http.GET


/**
 * This interface define:
 *  what endpoints exist
 *  what HTTP method they use
 *  what data type they return
 */
interface VedurAlertsApi {
    /**
     * Get all active weather alerts wiht detail
     * @return List of active alerts (may be empty)
     */
    @GET("/cap/v1/capbroker/active/detailed/all")
    suspend fun getActiveAlerts(): List<AlertDto>

    /***
     * Get weather forecast for specific days
     */
    @GET("weather/observations/aws/10min/latest")
    suspend fun getForecastToday(
        @Query("region_id") region: Int,
        @Query("parameters") parameters: String,
    ): Response<List<ForecastDto>>

    @GET("/quakes/events")
        suspend fun getEarthquakes(
            @Query("start_time") start: String,
            @Query("format") format: String
        ): Response<QuakeDto>


    @GET("/epos/volcano/general-information/volcanoes-status")
    suspend fun getVolcanos(
    ): Response<List<VolcanoDto>>
}