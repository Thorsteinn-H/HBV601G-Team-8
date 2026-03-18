package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDao
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient

/**
 * Calls the network layer
 * hides where the data comes from
 * keeps networking code out of UI
 */
class AlertRepository(
    private val alertDao: AlertDao
) {

    /**
     * @return List of AlertDto objects
     */
    suspend fun refreshAlerts(){
        val alerts =  VedurApiClient.api.getActiveAlerts()
        alertDao.clearAlerts()
        alertDao.insertAlerts(alerts)
    }


    suspend fun getAlerts(): List<AlertDto>{
        return alertDao.getAllAlerts()
    }
}