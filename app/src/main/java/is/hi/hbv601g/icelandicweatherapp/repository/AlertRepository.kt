package `is`.hi.hbv601g.icelandicweatherapp.repository

import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDao
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.network.ApiClient

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
    suspend fun saveAlerts(){
        val alerts =  ApiClient.api.getActiveAlerts()
        alertDao.clearAlerts()
        alertDao.insertAlerts(alerts)
    }


    suspend fun saveAlerts(alerts: List<AlertDto>){
        alertDao.clearAlerts()
        alertDao.insertAlerts(alerts)
    }
    suspend fun loadAlerts(): List<AlertDto>{
        return alertDao.getAllAlerts()
    }
}