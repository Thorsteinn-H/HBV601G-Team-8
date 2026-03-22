package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
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
     * call the API to retrive active alerts
     * clear old alerts stored in db
     * Inset the new alerts into the db
     */
    suspend fun refreshAlerts(){
        val alerts =  VedurApiClient.api.getActiveAlerts()
        alertDao.clearAlerts()
        alertDao.insertAlerts(alerts)
    }


    /**
     * @return List of AlertDto objects
     */
    suspend fun getAlerts(): List<AlertDto>{
        return alertDao.getAllAlerts()
    }
}