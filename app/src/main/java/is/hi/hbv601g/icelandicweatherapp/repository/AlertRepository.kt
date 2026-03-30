package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.AlertDto
import `is`.hi.hbv601g.icelandicweatherapp.model.Alert
import `is`.hi.hbv601g.icelandicweatherapp.model.toAlert
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient

/**
 * Calls the network layer
 * hides where the data comes from
 * keeps networking code out of UI
 */
class AlertRepository {

    /**
     * call the API to retrive active alerts
     * clear old alerts stored in db
     * Inset the new alerts into the db
     */
    suspend fun refreshAlerts(){
        val alerts =  VedurApiClient.api.getActiveAlerts()
    }


    /**
     * @return List of AlertDto objects
     */
    suspend fun getAlerts(): List<Alert>{
        return try{
            val response = VedurApiClient.api.getActiveAlerts()
            response.map{ it.toAlert()}
        } catch (e: Exception){
            emptyList()
        }
    }
}