package `is`.hi.hbv601g.icelandicweatherapp.repository

import android.util.Log
import `is`.hi.hbv601g.icelandicweatherapp.data.SunDto
import `is`.hi.hbv601g.icelandicweatherapp.network.SunApiClient

// responsible for fetching sun data from the API
class SunRepository {

    // fetches sunset data for a given latitude and longitude
    suspend fun getSun(lat: Double, lng: Double): SunDto? {
        return try{
            // network request to sun API
            val response = SunApiClient.sunApi.getSunsetSunriseToday(lat, lng)

            // check the request
            if(response.isSuccessful){
                response.body() // return if successful
            }else{
                null // null if unsuccessful
            }
        } catch (e: Exception){
            Log.e("SUN_ERROR", "Failed to fetch sun data", e)
            null
        }
    }
}