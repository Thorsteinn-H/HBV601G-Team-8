package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.QuakeDto
import `is`.hi.hbv601g.icelandicweatherapp.data.VolcanoDto
import `is`.hi.hbv601g.icelandicweatherapp.model.Alert
import `is`.hi.hbv601g.icelandicweatherapp.model.toAlert
import `is`.hi.hbv601g.icelandicweatherapp.network.VedurApiClient

/**
 * Calls the network layer
 * hides where the data comes from
 * keeps networking code out of UI
 */
class VedurAlertsRepository {

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
            // network request to get active alerts
            val response = VedurApiClient.api.getActiveAlerts()
            // convert AlertDto from API into Alert domain model
            response.map{ it.toAlert()}
        } catch (e: Exception){
            emptyList()
        }
    }


    /**
     * @return earthquake data from Vedur API
     */
    suspend fun getEarthquakes(start: String): QuakeDto? {
        return try {
            // network request with start time filter
            val response = VedurApiClient.api.getEarthquakes(
                start,
                format = "json"
            )
            // return body if successful
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }


    /**
     * @return volcano data from the Vedur API
     */
    suspend fun getVolcanos(): List<VolcanoDto> {
        return try {
            //network request to get vlocano list
            val response = VedurApiClient.api.getVolcanos()
            // return response body, or empty list
            response.body() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}