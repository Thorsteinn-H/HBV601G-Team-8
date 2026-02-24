package `is`.hi.hbv601g.icelandicweatherapp.network

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import retrofit2.http.GET

interface VedurApi {
    @GET("capbroker/active/detailed/all")
    suspend fun getActiveAlerts(): List<AlertDto>
}