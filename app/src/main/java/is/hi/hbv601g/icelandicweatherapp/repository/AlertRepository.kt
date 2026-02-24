package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient

class AlertRepository {
    suspend fun getAlerts(): List<AlertDto>{
        return ApiClient.api.getActiveAlerts()
    }
}