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
            val response = VedurApiClient.api.getActiveAlerts()
            Log.e("ALERT_DEBUG", "RAW RESPONSE: $response")
            response.map{ it.toAlert()}
        } catch (e: Exception){
            emptyList()
        }
    }


    suspend fun getEarthquakes(start: String): QuakeDto? {
        return try {
            val response = VedurApiClient.api.getEarthquakes(start)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getVolcanos(): List<VolcanoDto> {
        return try {
            val response = VedurApiClient.api.getVolcanos()
            response.body() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}