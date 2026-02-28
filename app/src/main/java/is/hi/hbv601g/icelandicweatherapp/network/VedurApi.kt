package `is`.hi.hbv601g.icelandicweatherapp.network

//import androidx.room.Query fyrir database ekki API köll
import retrofit2.http.Query
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.data.ForecastDto
import retrofit2.Response
import retrofit2.http.GET


/**
 * This interface define:
 *  what endpoints exist
 *  what HTTP method they use
 *  what data type they return
 */
interface VedurApi {
    /**
     * Get all active weather alerts wiht detail
     * @return List of active alerts (may be empty)
     */
    @GET("/cap/v1/capbroker/active/detailed/all")
    suspend fun getActiveAlerts(): List<AlertDto>

    /***
     * Get weather forecast for specific days
     */
    @GET("weather/observations/aws/hour/latest")
    suspend fun getForecastToday(
        @Query("region_id") region: Int,
        @Query("day_from") dayFrom:String,
        @Query("day_to") dayTo:String,
        @Query("parameters") parameters: String="basic",
        @Query("format")format: String="json",
        @Query("locale") locale: String ="is",
        @Query("order") order: String="asc",
        @Query("count") count:Int=1
    ): Response<List<ForecastDto>>
}