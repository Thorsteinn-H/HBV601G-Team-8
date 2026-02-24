package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
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
    @GET("capbroker/active/detailed/all")
    suspend fun getActiveAlerts(): List<AlertDto>
}