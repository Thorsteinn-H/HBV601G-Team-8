package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient

/**
 * Calls the network layer
 * hides where the data comes from
 * keeps networking code out of UI
 */
class AlertRepository {
    /**
     * @return List of AlertDto objects
     */
    suspend fun getAlerts(): List<AlertDto>{
        return ApiClient.api.getActiveAlerts()
    }
}